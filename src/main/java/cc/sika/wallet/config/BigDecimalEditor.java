package cc.sika.wallet.config;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author 吴畅
 * @创建时间 2023/3/3 - 15:39
 */
public class BigDecimalEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMAN);
        try {
            Number number = formatter.parse(text);
            BigDecimal bigDecimal = BigDecimal.valueOf(number.doubleValue());
            setValue(bigDecimal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
