package template;

import freemarker.core.HTMLOutputFormat;
import freemarker.core.JSONOutputFormat;
import freemarker.core.XMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TemplateResult {

	protected ServletContext context;
    protected Configuration cfg;
    protected DataModelFiller filler;

    public TemplateResult(ServletContext context) {
        this.context = context;
        init();
    }
    
    private void init() {
        cfg = new Configuration(Configuration.VERSION_2_3_26);
        if (context.getInitParameter("view.encoding") != null) {
            cfg.setOutputEncoding(context.getInitParameter("view.encoding"));
            cfg.setDefaultEncoding(context.getInitParameter("view.encoding"));
        }
        if (context.getInitParameter("view.template_directory") != null) {
            cfg.setServletContextForTemplateLoading(context, context.getInitParameter("view.template_directory"));
        } else {
            cfg.setServletContextForTemplateLoading(context, "templates");
        }
        if (context.getInitParameter("view.debug") != null && context.getInitParameter("view.debug").equals("true")) {   
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        } else {
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        }
        if (context.getInitParameter("view.date_format") != null) {
            cfg.setDateTimeFormat(context.getInitParameter("view.date_format"));
        }
        DefaultObjectWrapperBuilder owb = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_26);
        owb.setForceLegacyNonListCollections(false);
        owb.setDefaultDateType(TemplateDateModel.DATETIME);
        cfg.setObjectWrapper(owb.build());
    }

    protected Map getDefaultDataModel() {
    	
        Map default_data_model = new HashMap();

        if (filler != null) {
            filler.fillDataModel(default_data_model);
        }

        default_data_model.put("compiled_on", Calendar.getInstance().getTime());
        default_data_model.put("outline_tpl", context.getInitParameter("view.outline_template"));
        default_data_model.put("addMulti", context.getInitParameter("view.addMulti_template"));

        Map init_tpl_data = new HashMap();
        default_data_model.put("defaults", init_tpl_data);
        Enumeration parms = context.getInitParameterNames();
        while (parms.hasMoreElements()) {
            String name = (String) parms.nextElement();
            if (name.startsWith("view.data.")) {
                init_tpl_data.put(name.substring(10), context.getInitParameter(name));
            }
        }

        return default_data_model;
    }
    
    protected Map getRequestDataModel(HttpServletRequest request) {
        Map datamodel = new HashMap();
        Enumeration attrs = request.getAttributeNames();
        while (attrs.hasMoreElements()) {
            String attrname = (String) attrs.nextElement();
            datamodel.put(attrname, request.getAttribute(attrname));
        }
        return datamodel;
    }
    
    protected void process(String tplname, Map datamodel, Writer out) throws TemplateManagerExeption {
        Template t;
        Map<String, Object> localdatamodel = getDefaultDataModel();
        if (datamodel != null) {
            localdatamodel.putAll(datamodel);
        }
        String outline_name = (String) localdatamodel.get("outline_tpl");
        try {
            if (outline_name == null || outline_name.isEmpty()) {
                t = cfg.getTemplate(tplname);
            } else {
                t = cfg.getTemplate(outline_name);
                localdatamodel.put("content_tpl", tplname);
            }
            t.process(localdatamodel, out);
        } catch (IOException e) {
            throw new TemplateManagerExeption("Template error: " + e.getMessage(), e);
        } catch (TemplateException e) {
            throw new TemplateManagerExeption("Template error: " + e.getMessage(), e);
        }
    }
    
    protected void process2(String tplname, Map datamodel, Writer out) throws TemplateManagerExeption {
        Template t;
        Map<String, Object> localdatamodel = getDefaultDataModel();
        if (datamodel != null) {
            localdatamodel.putAll(datamodel);
        }
        String insert_name = (String) localdatamodel.get("addMulti");
        try {
                t = cfg.getTemplate(insert_name);
                localdatamodel.put("addMulti", tplname);                   
            t.process(localdatamodel, out);
        } catch (IOException e) {
            throw new TemplateManagerExeption("Template error: " + e.getMessage(), e);
        } catch (TemplateException e) {
            throw new TemplateManagerExeption("Template error: " + e.getMessage(), e);
        }
    }

    public void activate(String tplname, Map datamodel, HttpServletResponse response) throws TemplateManagerExeption {
        String contentType = (String) datamodel.get("contentType");
        if (contentType == null) {
            contentType = "text/html";
        }
        response.setContentType(contentType);

        switch (contentType) {
            case "text/html":
                cfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
                break;
            case "text/xml":
            case "application/xml":
                cfg.setOutputFormat(XMLOutputFormat.INSTANCE);
                break;
            case "application/json":
                cfg.setOutputFormat(JSONOutputFormat.INSTANCE);
                break;
            default:
                break;
        }

        String encoding = (String) datamodel.get("encoding");
        if (encoding == null) {
            encoding = cfg.getOutputEncoding();
        }
        response.setCharacterEncoding(encoding);

        try {
            process(tplname, datamodel, response.getWriter());
        } catch (IOException ex) {
            throw new TemplateManagerExeption("Template error: " + ex.getMessage(), ex);
        }
    }
    
    public void activate2(String tplname, Map datamodel, HttpServletResponse response) throws TemplateManagerExeption {
        String contentType = (String) datamodel.get("contentType");
        if (contentType == null) {
            contentType = "text/html";
        }
        response.setContentType(contentType);

        switch (contentType) {
            case "text/html":
                cfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
                break;
            case "text/xml":
            case "application/xml":
                cfg.setOutputFormat(XMLOutputFormat.INSTANCE);
                break;
            case "application/json":
                cfg.setOutputFormat(JSONOutputFormat.INSTANCE);
                break;
            default:
                break;
        }

        String encoding = (String) datamodel.get("encoding");
        if (encoding == null) {
            encoding = cfg.getOutputEncoding();
        }
        response.setCharacterEncoding(encoding);

        try {
            process2(tplname, datamodel, response.getWriter());
        } catch (IOException ex) {
            throw new TemplateManagerExeption("Template error: " + ex.getMessage(), ex);
        }
    }

    public void activate(String tplname, HttpServletRequest request, HttpServletResponse response) throws TemplateManagerExeption {
        Map datamodel = getRequestDataModel(request);
        activate(tplname, datamodel, response);
    }
    
    public void activate2(String tplname, HttpServletRequest request, HttpServletResponse response) throws TemplateManagerExeption {
        Map datamodel = getRequestDataModel(request);
        activate2(tplname, datamodel, response);
    }

    public void activate(String tplname, Map datamodel, OutputStream out) throws TemplateManagerExeption {
        String encoding = (String) datamodel.get("encoding");
        if (encoding == null) {
            encoding = cfg.getOutputEncoding();
        }
        try {
            process(tplname, datamodel, new OutputStreamWriter(out, encoding));
        } catch (UnsupportedEncodingException ex) {
            throw new TemplateManagerExeption("Template error: " + ex.getMessage(), ex);
        }
    }
	
}
