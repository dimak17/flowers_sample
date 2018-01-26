package com.impltech.reports.balance.second_balance;

import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.RectangleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalImageAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;

import java.awt.*;
import java.util.Locale;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by alex
 */
public class BalanceTemplates {

    public static final StyleBuilder rootStyle8;
    public static final StyleBuilder rootStyle9;
    public static final StyleBuilder rootStyle10;
    public static final StyleBuilder boldStyle8;
    public static final StyleBuilder boldStyle9;
    public static final StyleBuilder boldStyle10;
    public static final StyleBuilder italicStyle8;
    public static final StyleBuilder italicStyle9;
    public static final StyleBuilder italicStyle10;
    public static final StyleBuilder boldCenteredStyle8;
    public static final StyleBuilder boldCenteredStyle9;
    public static final StyleBuilder boldCenteredStyle10;
    public static final StyleBuilder columnStyle8;
    public static final StyleBuilder columnStyle9;
    public static final StyleBuilder columnStyle10;
    public static final StyleBuilder columnStyle8Left;
    public static final StyleBuilder columnStyle9Left;
    public static final StyleBuilder columnStyle10Left;
    public static final StyleBuilder columnTitleStyle8;
    public static final StyleBuilder columnTitleStyle9;
    public static final StyleBuilder columnTitleStyle10;
    public static final StyleBuilder groupStyle;
    public static final StyleBuilder subtotalStyle;
    public static final StyleBuilder footerStyle;
    public static final StyleBuilder headStyle14;
    public static final StyleBuilder headStyle10;
    public static final StyleBuilder boldheadStyle14;
    public static final StyleBuilder boldheadStyle10;
    public static final StyleBuilder leftHeadStyle;
    public static final StyleBuilder boldHeadStyle14;
    public static final StyleBuilder centerHeadStyle;
    public static final StyleBuilder imageStyle;
    public static final StyleBuilder rectangleStyle;
    public static final RectangleBuilder rectangleBackground;
    public static final ReportTemplateBuilder reportTemplate;

    static {
        rectangleStyle = stl.style().setRadius(5);
        rectangleBackground = cmp.rectangle().setStyle(rectangleStyle);
        headStyle14 = stl.style(5).setFontSize(14).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        headStyle10 = stl.style(5).setFontSize(10).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        boldheadStyle10 = stl.style(headStyle10).bold();
        boldheadStyle14 = stl.style(headStyle14).bold();
        leftHeadStyle = stl.style(5).setFontSize(10).setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE);
        centerHeadStyle = stl.style(5).setFontSize(10).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        boldHeadStyle14 = stl.style(headStyle14).bold();
        imageStyle = stl.style(5).setImageAlignment(HorizontalImageAlignment.LEFT, VerticalImageAlignment.MIDDLE);
        rootStyle8 = stl.style().setPadding(2).setFontSize(8);
        rootStyle9 = stl.style().setPadding(2).setFontSize(9);
        rootStyle10 = stl.style().setPadding(2).setFontSize(10);
        boldStyle8 = stl.style(rootStyle8).bold();
        boldStyle9 = stl.style(rootStyle9).bold();
        boldStyle10 = stl.style(rootStyle10).bold();
        italicStyle8 = stl.style(rootStyle8).italic();
        italicStyle9 = stl.style(rootStyle9).italic();
        italicStyle10 = stl.style(rootStyle10).italic();
        boldCenteredStyle8 = stl.style(boldStyle8).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        boldCenteredStyle9 = stl.style(boldCenteredStyle8).setFontSize(9);
        boldCenteredStyle10 = stl.style(boldCenteredStyle8).setFontSize(10);
        columnStyle8 = stl.style(rootStyle8).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE).setBorder(stl.pen1Point());
        columnStyle9 = stl.style(rootStyle9).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE).setBorder(stl.pen1Point());
        columnStyle10 = stl.style(rootStyle10).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE).setBorder(stl.pen1Point());
        columnStyle8Left = stl.style(columnStyle8).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        columnStyle9Left = stl.style(columnStyle8Left).setFontSize(9);
        columnStyle10Left = stl.style(columnStyle8Left).setFontSize(10);
        columnTitleStyle8 = stl.style(columnStyle8).setBorder(stl.pen1Point()).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE).setBackgroundColor(Color.LIGHT_GRAY).bold();
        columnTitleStyle9 = stl.style(columnTitleStyle8).setFontSize(9).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        columnTitleStyle10 = stl.style(columnTitleStyle8).setFontSize(10).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        footerStyle = stl.style().setFontSize(12).bold();
        groupStyle = stl.style(2).setFontSize(10).bold().setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
        subtotalStyle = stl.style(columnStyle10).bold().setBorder(stl.border(stl.pen1Point()));
        reportTemplate = template()
            .setLocale(Locale.ENGLISH)
            .setColumnStyle(columnStyle9)
            .setColumnTitleStyle(columnTitleStyle9)
            .setGroupStyle(groupStyle)
            .setGroupTitleStyle(columnTitleStyle9)
            .setSubtotalStyle(subtotalStyle)
            .highlightDetailEvenRows()
            .crosstabHighlightEvenRows();
    }
}
