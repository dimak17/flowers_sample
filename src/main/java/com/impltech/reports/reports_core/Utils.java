package com.impltech.reports.reports_core;

import com.impltech.reports.Templates;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalImageAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by alex
 */
public class Utils {

    private static ComponentBuilder<?, ?> barCode;
    private static ComponentBuilder<?, ?> secondSumarry;
    private static ComponentBuilder<?, ?> secondSumarry2;

    public ComponentBuilder<?, ?> selectionCode(Boolean bl, String codeNumber) {

        secondSumarry = cmp.horizontalList(
            cmp.text("    Payment terms: 30 days" +
                "\nThank you for your business")
                .setHorizontalTextAlignment(HorizontalTextAlignment.LEFT)
                .setStyle(stl.style(5).setFontSize(10).bold()
                    .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)));

        secondSumarry2 = cmp.horizontalList(
            cmp.text("    Payment terms: 30 days" +
                "\nThank you for your business")
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setStyle(stl.style(5).setFontSize(10).bold()
                    .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)));

        barCode = cmp.verticalList(bcode.code128(codeNumber)
            .setModuleWidth(3d).setFixedDimension(220, 82)
            .setStyle(Templates.boldStyle.setFontSize(18)
                .setVerticalImageAlignment(VerticalImageAlignment.MIDDLE)));

        if (bl.equals(true)) {
            HorizontalListBuilder list = cmp.horizontalList();
            list.add(secondSumarry, barCode);
            return list;
        }
        return secondSumarry2;
    }
}
