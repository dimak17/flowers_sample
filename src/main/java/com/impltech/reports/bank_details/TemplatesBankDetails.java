package com.impltech.reports.bank_details;

import com.impltech.reports.Templates;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

public class TemplatesBankDetails extends Templates {

    public static ComponentBuilder<?, ?>
    createBDTitleComponent(ComponentBuilder<?, ?> titleCompanyNameComponent,
                           ComponentBuilder<?, ?> titleBDDateComponent) {
        return cmp.horizontalList()
            .add(titleCompanyNameComponent, titleBDDateComponent);
    }

    public static ComponentBuilder<?, ?>
    createBOdyComponent(ComponentBuilder<?, ?> bodyBankDetails) {
        return cmp.verticalList()
            .add(bodyBankDetails)
            .add(cmp.verticalGap(10));
    }

    public static final StyleBuilder boldBankDetaisTitleStyle;
    public static final StyleBuilder boldBankDetailsRootStile;
    public static final StyleBuilder boldCenteredStyle;
    public static final StyleBuilder dateRightTopStyle;
    public static final StyleBuilder bodyStyle;

    static {
        boldBankDetaisTitleStyle = stl.style(5)
                .setFontSize(12);

        boldBankDetailsRootStile = stl.style(3).setFontSize(12);

        boldCenteredStyle = stl.style(boldBankDetailsRootStile)
            .setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE)
            .setTopPadding(11)
            .bold();

        dateRightTopStyle = stl.style(13)
            .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
            .setRightPadding(15)
            .boldItalic();

        bodyStyle = stl.style()
        .setTopPadding(30)
            .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
    }
}
