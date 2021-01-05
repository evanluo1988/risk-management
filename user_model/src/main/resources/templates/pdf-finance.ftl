<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <title>Title</title>

    <style>
        .container {
            margin-left: auto;
            margin-right: auto;
        }

        body {
            font-size: 14px;
            min-width: initial;
            background: none repeat scroll 0 0 #fff;
            color: #000;
            font-family: "Microsoft YaHei", WenQuanYi Micro Hei, "Heiti SC", "Lucida Sans Unicode", "Myriad Pro", "Hiragino Sans GB", Verdana, simsun;
            font-size: 10px;
            min-height: 100%;
            max-width: 700px;
            position: relative;
            margin: 0px;
        }

        h1, h3 {
            text-align: center;
        }

        table, table tr th, table tr td {
            border: 1px solid black;
        }

        td {
            width: 60px;
        }

        table {
            width: 700px;
            min-height: 25px;
            line-height: 25px;
            text-align: center;
            border-collapse: collapse;
        }

        .div-border {
            border: 1px solid black;
            box-sizing: border-box;
        }

    </style>
</head>
<body class="container">
<img src="entHealthEva.png"/>
<h1>第一部分 企业健康检测雷达</h1>
<div>
    <div>
        <div style="display: inline-block">
            企业名称：${data.entHealthAssessment.entHealthDetectionRadar.entName}<br/>
            统一社会信用代码：${data.entHealthAssessment.entHealthDetectionRadar.creditCode}<br/>
            法定代表人：${data.entHealthAssessment.entHealthDetectionRadar.lrName}
        </div>
        <div style="display: inline-block; margin-left: 250px;">
            所属行业：${data.entHealthAssessment.entHealthDetectionRadar.industry}<br/>
            注册资本：${data.entHealthAssessment.entHealthDetectionRadar.regCap}<br/>
            经营年限：${data.entHealthAssessment.entHealthDetectionRadar.establishPeriod}年
        </div>
    </div>

    <div>
        <h4>经营稳定性雷达</h4>
        <div><span>经营稳定性评分</span><span style="margin-left: 250px;">经营稳定性雷达说明</span></div>
        <table>
            <tr style="border: none">
                <td style="width: 30%">${data.entHealthAssessment.entHealthDialysis.fiveDRader.businessStabilityScore}
                    分
                </td>
                <td style="width: 70%;text-align: left">
                    ★报告主体经营稳定性得分为${data.entHealthAssessment.entHealthDialysis.fiveDRader.businessStabilityScore}分<br/>
                    ★经营稳定性衡量企业经营的变化情况，用于综合判断企业经营的稳定性。
                </td>
            </tr>
        </table>
    </div>

    <div>
        <h4>经营风险雷达</h4>
        <div><span>经营风险评分</span><span style="margin-left: 250px;">经营风险雷达说明</span></div>
        <table>
            <tr style="border: none">
                <td style="width: 30%">${data.entHealthAssessment.entHealthDialysis.fiveDRader.businessRiskScore}分</td>
                <td style="width: 70%;text-align: left">
                    ★报告主体经营风险得分为${data.entHealthAssessment.entHealthDialysis.fiveDRader.businessRiskScore}分<br/>
                    ★经营风险反映企业经营中发生与出现的各种经营相关的风险与异常情况。
                </td>
            </tr>

        </table>
    </div>

    <div>
        <h4>法律风险雷达</h4>
        <div><span>法律风险评分</span><span style="margin-left: 250px;">法律风险雷达说明</span></div>
        <table>
            <tr style="border: none">
                <td style="width: 30%">${data.entHealthAssessment.entHealthDialysis.fiveDRader.legalRiskScore}分</td>
                <td style="width: 70%;text-align: left">
                    ★报告主体法律风险得分为${data.entHealthAssessment.entHealthDialysis.fiveDRader.legalRiskScore}分<br/>
                    ★法律风险反映企业涉诉方面的风险情况。
                </td>
            </tr>

        </table>
    </div>
</div>

<h1>第二部分 企业健康监测透析</h1>
<div>
    <div>
        <h4>五维雷达透析一览表</h4>
        <table>
            <thead>
            <tr>
                <th>评价雷达</th>
                <th colspan="2">经营稳定性</th>
                <th colspan="2">经营风险</th>
                <th colspan="2">法律风险</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td scope="row">综合得分</td>
                <td colspan="2">${data.entHealthAssessment.entHealthDialysis.fiveDRader.businessStabilityScore}</td>
                <td colspan="2">${data.entHealthAssessment.entHealthDialysis.fiveDRader.businessRiskScore}</td>
                <td colspan="2">${data.entHealthAssessment.entHealthDialysis.fiveDRader.legalRiskScore}</td>
            </tr>
            <tr>
                <td rowspan="4">评价维度&amp;风险关注项</td>
                <td>经营持续性</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "GS_BUSINESS_CONTINUITY">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
                <td>工商异常</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "GS_BUSINESS_ANOMALIES">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
                <td>案件风险</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "SF_CASE_RISK">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
            </tr>
            <tr>
                <td>经营变动</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "GS_BUSINESS_CHANGE">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
                <td>经营异常</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "GS_MANAGE_ANOMALIES">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
                <td>涉诉行为</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "SF_LITIGATION_RELATED_BEHAVIOR">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
            </tr>
            <tr>
                <td>控制权稳定性</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "GS_CONTROL_STABILITY">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
                <td>行政处罚</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "GS_ADMINISTRATIVE_SANCTION">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>投资方能力</td>
                <td>
                    <#list data.entHealthAssessment.entHealthDialysis.fiveDRader.fiveDRaderItemList as fiveDrader>
                        <#if fiveDrader.quotaCode == "GS_INVESTOR_CAPACITY">
                            ${fiveDrader.quotaValue}项
                        </#if>
                    </#list>
                </td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
        <span>注：风险关注项详见各个评价雷达透析中的红色底色提示项</span>
    </div>

    <div>
        <h4>经营稳定性透析</h4>
        <table>
            <thead>
            <tr>
                <td>评价雷达</td>
                <td>评价维度</td>
                <td>时点/时段</td>
                <td>关注项</td>
                <td>客户实际值</td>
            </tr>
            </thead>
            <tbody>
            <#list data.entHealthAssessment.entHealthDialysis.businessStabilityList as businessStability>
            <tr>
                <td>${businessStability.evaluationRadar}</td>
                <td>${businessStability.evaluationDimension}</td>
                <td>${businessStability.timeInterval}</td>
                <td>${businessStability.concerns}</td>
                <td>${businessStability.actualValue}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div>
        <h4>经营风险透析</h4>
        <table>
            <thead>
            <tr>
                <td>评价雷达</td>
                <td>评价维度</td>
                <td>时点/时段</td>
                <td>关注项</td>
                <td>客户实际值</td>
            </tr>
            </thead>
            <tbody>
            <#list data.entHealthAssessment.entHealthDialysis.businessRiskList as businessRisk>
            <tr>
                <td>${businessRisk.evaluationRadar}</td>
                <td>${businessRisk.evaluationDimension}</td>
                <td>${businessRisk.timeInterval}</td>
                <td>${businessRisk.concerns}</td>
                <td>${businessRisk.actualValue}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div>
        <h4>法律风险透析</h4>
        <table>
            <thead>
            <tr>
                <td>评价雷达</td>
                <td>评价维度</td>
                <td>时点/时段</td>
                <td>关注项</td>
                <td>客户实际值</td>
            </tr>
            </thead>
            <tbody>
            <#list data.entHealthAssessment.entHealthDialysis.legalRiskList as legalRisk>
            <tr>
                <td>${legalRisk.evaluationRadar}</td>
                <td>${legalRisk.evaluationDimension}</td>
                <td>${legalRisk.timeInterval}</td>
                <td>${legalRisk.concerns}</td>
                <td>${legalRisk.actualValue}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

</div>

<img src="entHealthDet.png"/>
<br/>
<br/>
<h1>第一部分 企业背景详情</h1>
<h3>一、企业注册信息</h3>
<div>
    <div>
        <h4>企业基本信息</h4>
        <table>
            <tbody>
            <tr>
                <td>法定代表人</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.lrName}</td>
                <td>企业（机构）类型</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.entType}</td>
            </tr>
            <tr>
                <td>注册资本</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.regCap}</td>
                <td>币种</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.regCapCur}</td>
            </tr>
            <tr>
                <td>实缴资本</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.recCap}</td>
                <td>最后年检年度</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.ancheYear}</td>
            </tr>
            <tr>
                <td>行业</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.industry}</td>
                <td>注销日期</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.canDate}</td>
            </tr>
            <tr>
                <td>经营期限自</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.openFrom}</td>
                <td>吊销日期</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.revDate}</td>
            </tr>
            <tr>
                <td>工商注册号</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.regNo}</td>
                <td>员工人数</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.empNum}</td>
            </tr>
            <tr>
                <td>统一社会信用代码</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.creditCode}</td>
                <td>经营期限至</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.openTo}</td>
            </tr>
            <tr>
                <td>注册地址</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.address}</td>
                <td>登记机关</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.regOrg}</td>
            </tr>
            <tr>
                <td>经营范围</td>
                <td>${data.entHealthDetails.entRegInformation.entBasicInformation.operateScope}</td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>

    <div>
        <h4>主要人员</h4>
        <table>
            <tbody>
            <tr>
                <td>姓名</td>
                <#list data.entHealthDetails.entRegInformation.personnelList as personnel>
                    <td>${personnel.name}</td>
                </#list>
            </tr>
            <tr>
                <td>职务</td>
                <#list data.entHealthDetails.entRegInformation.personnelList as personnel>
                    <td>${personnel.position}</td>
                </#list>
            </tr>
            </tbody>
        </table>
    </div>

    <div>
        <h4>股东信息</h4>
        <table>
            <thead>
            <tr>
                <td>股东名称</td>
                <td>认缴出资额</td>
                <td>出资日期</td>
                <td>出资比例</td>
                <td>股东类型</td>
                <td>出资方式</td>
            </tr>
            </thead>
            <tbody>
                <#list data.entHealthDetails.entRegInformation.shareholderList as shareholder>
                <tr>
                    <td>${shareholder.shareHolderName}</td>
                    <td>${shareholder.subconam}</td>
                    <td>${shareholder.conDate}</td>
                    <td>${shareholder.fundedratio}</td>
                    <td>${shareholder.invtype}</td>
                    <td>${shareholder.conform}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>

<#--<div>-->
<#--<h4>分支机构</h4>-->
<#--<table>-->
<#--<thead>-->
<#--<tr>-->
<#--<td>序号</td>-->
<#--<td>企业名称</td>-->
<#--<td>负责人</td>-->
<#--<td>成立日期</td>-->
<#--<td>经营状态</td>-->
<#--</tr>-->
<#--</thead>-->
<#--<tbody>-->
<#--<tr>-->
<#--<td>1</td>-->
<#--<td>****有限公司成都第二分公司</td>-->
<#--<td>李*兰</td>-->
<#--<td>2020/2/3</td>-->
<#--<td>存续</td>-->
<#--</tr>-->
<#--<tr>-->
<#--<td>2</td>-->
<#--<td>****有限公司成都第三分公司</td>-->
<#--<td>梁*兵</td>-->
<#--<td>2020/2/3</td>-->
<#--<td>存续</td>-->
<#--</tr>-->
<#--</tbody>-->
<#--</table>-->
<#--</div>-->
</div>

<h3>二、企业经营发展信息</h3>
<div>
    <div>
        <h4>信息变更记录</h4>
        <table>
            <thead>
            <tr>
                <td>序号</td>
                <td>年度</td>
                <td>日期</td>
                <td>变更项目</td>
                <td>变更前</td>
                <td>变更后</td>
            </tr>
            </thead>
            <tbody>
            <#list data.entHealthDetails.entAlterList as entAlter>
            <tr>
                <td>${entAlter_index+1}</td>
                <td>${entAlter.altdateYear}</td>
                <td>${entAlter.altdate}</td>
                <td>${entAlter.altitem}</td>
                <td>${entAlter.altbe}</td>
                <td>${entAlter.altaf}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>

<h1>第二部分 企业风险详情</h1>
<h3>一、企业经营异常详情</h3>
<div>
    <div>
        <h4>行政处罚</h4>
        <table>
            <thead>
            <tr>
                <td>处罚决定文号</td>
                <td>处罚决定书签发日期</td>
                <td>公示日期</td>
                <td>处罚机关</td>
                <td>主要违法事实</td>
                <td>处罚种类</td>
                <td>处罚结果</td>
            </tr>
            </thead>
            <tbody>
            <#list data.entHealthDetails.entAbnormalDetails.caseinfoList as caseinfo>
            <tr>
                <td>${caseinfo.pendecno}</td>
                <td>${caseinfo.pendecissdate}</td>
                <td>${caseinfo.publicdate}</td>
                <td>${caseinfo.penauth}</td>
                <td>${caseinfo.illegfact}</td>
                <td>${caseinfo.pentype}</td>
                <td>${caseinfo.penresult}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div>
        <h4>股权冻结</h4>
        <table>
            <thead>
            <tr>
                <td>序号</td>
                <td>冻结文号</td>
                <td>冻结机关</td>
                <td>冻结起始日期</td>
                <td>冻结截至日期</td>
                <td>解冻日期</td>
                <td>冻结金额</td>
                <td>解冻文号</td>
                <td>解冻说明</td>
            </tr>
            </thead>
            <tbody>
            <#list data.entHealthDetails.entAbnormalDetails.entSharesfrostList as entSharesfrost>
            <tr>
                <td>${entSharesfrost_index+1}</td>
                <td>${entSharesfrost.frodocno}</td>
                <td>${entSharesfrost.froauth}</td>
                <td>${entSharesfrost.frofrom}</td>
                <td>${entSharesfrost.froto}</td>
                <td>${entSharesfrost.thawdate}</td>
                <td>${entSharesfrost.froam}</td>
                <td>${entSharesfrost.thawdocno}</td>
                <td>${entSharesfrost.thawcomment}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div>
        <h4>企业异常名录</h4>
        <table>
            <thead>
            <tr>
                <td>序号</td>
                <td>企业名称</td>
                <td>列入日期</td>
                <td>列入原因</td>
                <td>移出日期</td>
                <td>退出异常名录原因</td>
            </tr>
            </thead>
            <tbody>
            <#list data.entHealthDetails.entAbnormalDetails.entExceptionList as entException>
            <tr>
                <td>${entException_index+1}</td>
                <td>${entException.entname}</td>
                <td>${entException.indate}</td>
                <td>${entException.inreason}</td>
                <td>${entException.outdate}</td>
                <td>${entException.outreason}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div>
        <h4>企业清算信息</h4>
        <table>
            <thead>
            <tr>
                <td>序号</td>
                <td>清算负责人</td>
                <td>清算组成员</td>
                <td>清算完结情况</td>
                <td>清算完结日期</td>
                <td>债务承接人</td>
                <td>债权承接人</td>
            </tr>
            </thead>
            <tbody>
            <#list data.entHealthDetails.entAbnormalDetails.entLiquidationList as entLiquidation>
            <tr>
                <td>${entLiquidation_index+1}</td>
                <td>${entLiquidation.ligprincipal}</td>
                <td>${entLiquidation.liqmen}</td>
                <td>${entLiquidation.ligst}</td>
                <td>${entLiquidation.ligenddate}</td>
                <td>${entLiquidation.debttranee}</td>
                <td>${entLiquidation.claimtranee}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<h3>二、企业涉诉详情</h3>
<div>
    <div>
        <h4>涉诉案件列表</h4>
        <#list data.entHealthDetails.litigaCaseList as litigaCase>
            <h5>案件编号：${litigaCase_index+1}</h5>
        <table>
            <thead>
            <tr>
                <td>案号</td>
                <td>案件类型</td>
                <td>案由</td>
                <td>诉讼地位</td>
                <td>间隔时间（年）</td>
                <td>涉案金额（元）</td>
                <td>法院等级</td>
                <td>审理结果</td>
                <td>案件结果对客户的影响</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${litigaCase.caseCode}</td>
                <td>${litigaCase.docClass}</td>
                <td>${litigaCase.caseReason}</td>
                <td>${litigaCase.lawStatus}</td>
                <td>${litigaCase.intervalYear}</td>
                <td>${litigaCase.payment}</td>
                <td>${litigaCase.courtLevel}</td>
                <td>${litigaCase.sentenceBrief}</td>
                <td>${litigaCase.sentenceEffect}</td>
            </tr>
            </tbody>
        </table>
        </#list>
    </div>
</div>

</body>
</html>