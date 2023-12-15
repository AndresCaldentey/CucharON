package Negocio;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.cucharon.R;

public class CustomFontTextView extends androidx.appcompat.widget.AppCompatTextView {

    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        // Separar las palabras
        String[] words = text.toString().split("\\s+");

        if (words.length > 1) {
            // Tomar la última palabra
            String penultimateWord = words[words.length - 2];
            String lastWord = words[words.length - 1];

            // Crear un SpannableStringBuilder para aplicar el estilo solo a la última palabra
            SpannableStringBuilder spannable = new SpannableStringBuilder(text);
            int penultimateWordStart = text.toString().lastIndexOf(penultimateWord);
            int penultimateWordEnd = penultimateWordStart + penultimateWord.length();
            int lastWordStart = text.toString().lastIndexOf(lastWord);
            int lastWordEnd = lastWordStart + lastWord.length();

            spannable.insert(lastWordStart, "  ");

            // Establecer el estilo de la última palabra utilizando el estilo definido en styles.xml
            spannable.setSpan(new TextAppearanceSpan(getContext(), R.style.Cursiva), lastWordStart + 2, lastWordEnd + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            super.setText(processText(spannable), type);
        } else {
            // Si no hay palabras, simplemente establecer el texto normal
            super.setText(text, type);
        }
    }

    private CharSequence processText(CharSequence text) {
        // Divide el texto en líneas
        String[] lines = text.toString().split("\\n");

        if (lines.length > 1) {
            // Procesa las líneas y añade dos espacios al inicio si es necesario
            for (int i = 1; i < lines.length; i++) {
                if (lines[i - 1].endsWith(" ")) {
                    // Añade dos espacios al inicio de la línea actual
                    lines[i] = "  " + lines[i];
                }
            }

            // Une las líneas de nuevo en un solo CharSequence
            return new SpannableStringBuilder(TextUtils.join("\n", lines));
        } else {
            return text;
        }
    }
}
