package by.chyrkun.training.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class InfoDateTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        Locale locale;
        String lang = pageContext.getSession().getAttribute("lang").toString();
        if (lang.matches("[\\w]{2}_[\\w]{2}")) {
            String language = lang.substring(0, lang.indexOf("_"));
            String country = lang.substring(lang.indexOf("_") + 1);
            locale = new Locale(language, country);
        }else {
            locale = Locale.getDefault();
        }
        String pattern = "dd MMMM yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, locale);
        LocalDate today = LocalDate.now();
        String date = dateTimeFormatter.format(today);
        String time = "<b> " + date + " </b>";
        try {
            JspWriter out = pageContext.getOut();
            out.write(time);
        } catch (IOException ex) {
            throw new JspException(ex.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
