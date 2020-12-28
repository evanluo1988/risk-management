package com.springboot.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.apache.commons.io.IOUtils;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.tool.xml.NoCustomContextException;
import com.itextpdf.tool.xml.Tag;
import com.itextpdf.tool.xml.WorkerContext;
import com.itextpdf.tool.xml.exceptions.LocaleMessages;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;
import org.apache.commons.lang.StringUtils;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 刘宏飞
 * @Date 2020/12/24 14:08
 * @Version 1.0
 */
public class ITextUtils {
    public static void savePdf(OutputStream out, String html) {
        Document document = new Document(PageSize.A4, 50, 50, 60, 60);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(html));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public static void convertHtmlToPdf(OutputStream out, String html) {
        Document document = null;
        InputStream is = null;
        try {
            document = new Document(PageSize.A4, 50, 50, 60, 60);
            final PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

            final CssFilesImpl cssFiles = new CssFilesImpl();
            cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
            final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
            final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider()));
            hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
            final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
            final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
            final XMLWorker worker = new XMLWorker(pipeline, true);
            final Charset charset = Charset.forName("UTF-8");
            final XMLParser xmlParser = new XMLParser(true, worker, charset);

            is = IOUtils.toInputStream(html, StandardCharsets.UTF_8.name());
            xmlParser.parse(is, charset);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            document.close();
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ImageTagProcessor extends com.itextpdf.tool.xml.html.Image {
        private final Logger logger = LoggerFactory.getLogger(getClass());
        /*
         * (non-Javadoc)
         * @see com.itextpdf.tool.xml.TagProcessor#endElement(com.itextpdf.tool.xml.Tag, java.util.List, com.itextpdf.text.Document)
         */
        @Override
        public List<Element> end(final WorkerContext ctx, final Tag tag, final List<Element> currentContent) {
            final Map<String, String> attributes = tag.getAttributes();
            String src = attributes.get(HTML.Attribute.SRC);
            List<Element> elements = new ArrayList<Element>(1);
            if (null != src && src.length() > 0) {
                Image img = null;
                if (StringUtils.isNotBlank(src)) {
                    final String base64Data = src.substring(src.indexOf(",") + 1);
                    try {
                        img = Image.getInstance(getClass().getResource("/templates/"+src));
                        //img = Image.getInstance(Base64.decode(base64Data));
                    } catch (Exception e) {
                        logger.error(String.format(LocaleMessages.getInstance().getMessage(LocaleMessages.HTML_IMG_RETRIEVE_FAIL), src), e);
                    }
                    if (img != null) {
                        try {
                            final HtmlPipelineContext htmlPipelineContext = getHtmlPipelineContext(ctx);
                            elements.add(getCssAppliers().apply(new Chunk((com.itextpdf.text.Image) getCssAppliers().apply(img, tag, htmlPipelineContext), 0, 0, true), tag,
                                    htmlPipelineContext));
                        } catch (NoCustomContextException e) {
                            throw new RuntimeWorkerException(e);
                        }
                    }
                }

                if (img == null) {
                    elements = super.end(ctx, tag, currentContent);
                }
            }
            return elements;
        }
    }

}