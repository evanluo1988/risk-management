set global log_bin_trust_function_creators=TRUE;

delimiter $$
create function check_number(val VARCHAR(100)) returns VARCHAR(100)
begin
	  declare tmp_num  varchar(30);
		declare rtv VARCHAR(100) default null;
		
		SET tmp_num = replace(trim(val), ' ', '');
		SET tmp_num = replace(trim(tmp_num), ',', '');
    SET tmp_num = replace(trim(tmp_num), '，', '');
		SET tmp_num = replace(trim(tmp_num), '¥', '');
		
		IF instr(tmp_num,'%')>0 THEN
			SET tmp_num = replace(tmp_num,'%','');
			IF !(tmp_num REGEXP '[^0-9.]') THEN
			  SET tmp_num = tmp_num *0.01 ;
			END IF;
		END IF;
		
		IF instr(tmp_num,'万')>0  THEN
			SET tmp_num = replace(tmp_num, '万元', '');
      SET tmp_num = replace(tmp_num, '万', '');
		END IF;
		
		IF !(tmp_num REGEXP '[^0-9.%]') THEN
			SET rtv = tmp_num;
		END IF;
    return rtv;	
end
$$
delimiter;


delimiter $$
create function instr_(str VARCHAR(100), substr VARCHAR(100), position int) returns int
 return LOCATE(substr,SUBSTRING(str, position, LENGTH(str))) + position-1
$$
delimiter;


delimiter $$
create function check_date(val VARCHAR(100)) returns VARCHAR(100)
begin
		declare trim_date varchar(30);
		declare month_num varchar(20);
		declare strlength int;
		declare rtv VARCHAR(100) default null;
		
		SET trim_date = trim(replace(val, ',', '-'));
		SET trim_date = trim(replace(upper(trim_date), 'NULL', ''));
		SET trim_date = trim(replace(replace(replace(trim_date, '年', '-'),'月','-'),'日',''));
		SET trim_date = trim(replace(trim_date, '#', ''));
		SET trim_date = trim(replace(replace(trim_date, '.', '-'), '/', '-'));
		
		SET strlength = length(trim_date);
		IF strlength = 0 THEN
			return null;
		END IF;
		IF trim_date = '长期' THEN
			return '9999-12-31';
		END IF;
		IF trim_date REGEXP '[^0-9T:\\- ]' THEN
			return null;
		END IF;
		IF strlength = 4 THEN
			SET trim_date = DATE_FORMAT(STR_TO_DATE(CONCAT(trim_date, '-01-01'), '%Y-%m-%d'), '%Y-%m-%d');
		#yyyy-m / yyyy-mm / mm-yy
		ELSEIF strlength = 6 OR strlength = 7 THEN
			IF !(substr(trim_date, 1, 4) REGEXP '[^0-9]') THEN
				SET trim_date = DATE_FORMAT(STR_TO_DATE(CONCAT(trim_date, '-01'), '%Y-%m-%d'), '%Y-%m-%d');
			ELSE
				SET trim_date = DATE_FORMAT(STR_TO_DATE(CONCAT('01-',trim_date), '%d-%m-%Y'), '%Y-%m-%d');
			END IF;
		#yyyy-m-d / yyyy-mm-d / yyyy-mm-dd / dd-mm-yyyy / mm-dd-yyyy
		ELSEIF strlength = 8 OR strlength = 9 OR strlength = 10 THEN
		  
			IF !(substr(trim_date, 1, 4) REGEXP '[^0-9]')  THEN
				SET trim_date = DATE_FORMAT(STR_TO_DATE(trim_date, '%Y-%m-%d'), '%Y-%m-%d');
			ELSE
				SET month_num = substr(trim_date,
                          instr_(trim_date, '-', 1) + 1,
                          instr_(trim_date, '-', instr_(trim_date, '-', 1) + 1) -
                          instr_(trim_date, '-', 1) - 1);
				IF ( !(month_num REGEXP '[^0-9]') and month_num > 12) THEN
					SET trim_date = DATE_FORMAT(STR_TO_DATE(trim_date, '%m-%d-%Y'), '%Y-%m-%d');
				ELSE
					SET trim_date = DATE_FORMAT(STR_TO_DATE(trim_date, '%d-%m-%Y'), '%Y-%m-%d');
				END IF;
			END IF;
		#yyyy-mm-dd hh24
		ELSEIF strlength = 11 or strlength = 12 THEN
			SET trim_date = DATE_FORMAT(STR_TO_DATE(trim_date, '%Y-%m-%d%H'),'%Y-%m-%d %H:%i:%s');
		#yyyy-mm-dd hh24:mi
		ELSEIF strlength = 13 or strlength = 14 or strlength = 15 THEN
			SET trim_date = DATE_FORMAT(STR_TO_DATE(trim_date, '%Y-%m-%d%H:%i'), '%Y-%m-%d %H:%i:%s');
		#yyyy-mm-dd hh24:mi:ss
		ELSEIF strlength = 18 or strlength = 19 THEN
			SET trim_date = DATE_FORMAT(STR_TO_DATE(replace(trim_date, 'T', ' '), '%Y-%m-%d %H:%i:%s'), '%Y-%m-%d %H:%i:%s');
		ELSE
			SET trim_date = DATE_FORMAT(STR_TO_DATE(substr(replace(trim_date, 'T', ' '), 1, 19),'%Y-%m-%d %H:%i:%s'), '%Y-%m-%d %H:%i:%s');
		END IF;
		
		IF substr(trim_date, 1, 2) = '00' THEN
      SET trim_date = CONCAT('20', substr(trim_date, 3));
    END IF;
		
		SET rtv = trim_date;
		
    return rtv;
end
$$
delimiter;