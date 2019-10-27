package edu.umd.cs.findbugs;




import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import net.sf.saxon.trans.SymbolicName;

import java.util.List;
import java.util.Set;

/**
 * This class is used to saveBugReporter in a location.
 */
public class SaveBugReporter {
    public SaveBugReporter(){
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            String PdfName=AnalyseCommand.bugreporterLocation+"\\"+"bugReporter.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(PdfName));
            document.open();
            BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese,15,Font.BOLD);//加入document：
           // BaseFont.createFont()
            PdfPTable tableTitle=new PdfPTable(1);
            tableTitle.setWidthPercentage(100); // 宽度100%填充
            tableTitle.setSpacingBefore(10f); // 前间距
            tableTitle.setSpacingAfter(10f); // 后间距
            float[] columnWidths1 = {1.8f };
            tableTitle.setWidths(columnWidths1);
            PdfPCell cellTitle=new PdfPCell(new Paragraph("规则设计平台扫描报告",new
                    Font(bfChinese,20,Font.BOLD) ));
            cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTitle.disableBorderSide(15);
            tableTitle.addCell(cellTitle);
            PdfPCell cellTime=new PdfPCell(new Paragraph(LaunchAppropriateUI.startTime+" 至 "+LaunchAppropriateUI.endTime,new
                    Font(bfChinese,20,Font.BOLD) ));
            cellTime.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTime.disableBorderSide(15);
            tableTitle.addCell(cellTime);

            document.add(tableTitle);

           /* document.add(new Chunk("规则设计平台扫描报告",new
                    Font(bfChinese,20,Font.BOLD) ));//title没有用  不会显示
            document.add(new Paragraph(LaunchAppropriateUI.startTime+" 至 "+LaunchAppropriateUI.endTime,FontChinese));// Paragraph添加文本*/
            /*检测概要*/

            document.add(new Paragraph("1.检测概要",FontChinese));
            document.add(new Paragraph("1.1 源代码信息",FontChinese));

            Font FontNomal= new Font(bfChinese,15,Font.NORMAL);//加入document：
            PdfPTable table1=new PdfPTable(2);
            table1.setWidthPercentage(100); // 宽度100%填充
            table1.setSpacingBefore(10f); // 前间距
            table1.setSpacingAfter(10f); // 后间距
            List<PdfPRow> listRow = table1.getRows();
            //设置列宽
            float[] columnWidths = { 0.9f, 0.9f };
            table1.setWidths(columnWidths);
            PdfPCell cells1= new PdfPCell();
        //    PdfPRow row1_1 = new PdfPRow(cells1);
            cells1 = new PdfPCell(new Paragraph("源代码基本信息",FontNomal));//单元格内容
        //    cells1.setBorderColor(new BaseColor(0,255,255));//边框验证
            cells1.setPaddingLeft(20);//左填充20
            cells1.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1.setBackgroundColor(new BaseColor(0,255,255));
            cells1.setColspan(2);
            table1.addCell(cells1);

            PdfPCell[] cells2 = new PdfPCell[2];
            PdfPRow  row1_2 = new PdfPRow(cells2);
            cells2[0] = new PdfPCell(new Paragraph("开发语言",FontNomal));
            cells2[0].setBorderColor(BaseColor.BLACK);//边框验证
            cells2[0].setPaddingLeft(20);//左填充20
            cells2[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells2[1] = new PdfPCell(new Paragraph("Java"));
            cells2[1].setBorderColor(BaseColor.BLACK);//边框验证
            cells2[1].setPaddingLeft(20);//左填充20
            cells2[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells2[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

            PdfPCell[] cells3 = new PdfPCell[2];
            PdfPRow  row1_3 = new PdfPRow(cells3);
            cells3[0] = new PdfPCell(new Paragraph("源代码文件数",FontNomal));
            cells3[0].setBorderColor(BaseColor.BLACK);//边框验证
            cells3[0].setPaddingLeft(20);//左填充20
            cells3[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells3[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells3[1] = new PdfPCell(new Paragraph(BaseInformation.classFiles+""));
            cells3[1].setBorderColor(BaseColor.BLACK);//边框验证
            cells3[1].setPaddingLeft(20);//左填充20
            cells3[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells3[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

            listRow.add(row1_2);
            listRow.add(row1_3);
            document.add(table1);

            savePriority(document,FontNomal);
            saveBugs(document,FontNomal);
            saveDetailInfo(document,FontNomal);
          document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePriority(Document document,Font Font){
        /*等级统计*/
        try {
            BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese,15,Font.BOLD);
            document.add(new Paragraph("1.2 等级统计", FontChinese));
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100); // 宽度100%填充
            table.setSpacingBefore(10f); // 前间距
            table.setSpacingAfter(10f); // 后间距
            List<PdfPRow> listRow = table.getRows();
            float[] columnWidths = {0.6f, 0.6f, 0.6f};
            table.setWidths(columnWidths);

            PdfPCell cell1=new PdfPCell(new Paragraph("漏洞等级统计表",Font));
            cell1.setBorderColor(BaseColor.BLACK);//边框验证
            cell1.setPaddingLeft(20);//左填充20
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell1.setBackgroundColor(new BaseColor(0,255,255));
            cell1.setColspan(3);
            table.addCell(cell1);

            PdfPCell[] cell22=new PdfPCell[3];
            PdfPRow  row2_2 = new PdfPRow(cell22);
            cell22[0]=new PdfPCell(new Paragraph("等级",Font));
            cell22[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中

            cell22[1]=new PdfPCell(new Paragraph("数量",Font));
            cell22[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中

            cell22[2]=new PdfPCell(new Paragraph("占比",Font));
            cell22[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            listRow.add(row2_2);

            PdfPCell[] cell23=new PdfPCell[3];
            PdfPRow  row2_3 = new PdfPRow(cell23);
            int total=BaseInformation.priorityHigh+BaseInformation.priorityNormal+BaseInformation.priorityLow;
            cell23[0]=new PdfPCell(new Paragraph("高",Font));
            cell23[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中

            cell23[1]=new PdfPCell(new Paragraph(BaseInformation.priorityHigh+""));
            cell23[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            if(total!=0){
            cell23[2]=new PdfPCell(new Paragraph(Math.round(BaseInformation.priorityHigh*1.0/total)+""));}
            else {
                cell23[2]=new PdfPCell(new Paragraph("None"));
            }
            cell23[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            listRow.add(row2_3);

            PdfPCell[] cell24=new PdfPCell[3];
            PdfPRow  row2_4 = new PdfPRow(cell24);
            cell24[0]=new PdfPCell(new Paragraph("中",Font));
            cell24[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中

            cell24[1]=new PdfPCell(new Paragraph(BaseInformation.priorityNormal+""));
            cell24[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            if(total!=0){
            cell24[2]=new PdfPCell(new Paragraph(Math.round(BaseInformation.priorityNormal*1.0/total)+""));}
            else {
                cell24[2]=new PdfPCell(new Paragraph("None"));
            }
            cell24[2].setHorizontalAlignment(Element.ALIGN_CENTER);
            listRow.add(row2_4);

            PdfPCell[] cell25=new PdfPCell[3];
            PdfPRow  row2_5 = new PdfPRow(cell25);
            cell25[0]=new PdfPCell(new Paragraph("低",Font));
            cell25[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell25[1]=new PdfPCell(new Paragraph(BaseInformation.priorityLow+""));
            cell25[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            if(total!=0){
            cell25[2]=new PdfPCell(new Paragraph(Math.round(BaseInformation.priorityLow*1.0/total)+""));}
            else {
                cell25[2]=new PdfPCell(new Paragraph("None"));
            }
            cell25[2].setHorizontalAlignment(Element.ALIGN_CENTER);
            listRow.add(row2_5);

            document.add(table);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveBugs(Document document,Font font){
        try{
            BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese,15,Font.BOLD);
            document.add(new Paragraph("1.3 分类统计",FontChinese));
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100); // 宽度100%填充
            table.setSpacingBefore(10f); // 前间距
            table.setSpacingAfter(10f); // 后间距
            List<PdfPRow> listRow = table.getRows();
            float[] columnWidths = {0.6f, 0.3f, 0.3f, 0.3f, 0.3f};
            table.setWidths(columnWidths);

            PdfPCell cell1=new PdfPCell(new Paragraph("数量",font));
            cell1.setBorderColor(BaseColor.BLACK);//边框验证
            cell1.setPaddingLeft(20);//左填充20
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cell1.setBackgroundColor(new BaseColor(0,255,255));
            cell1.setColspan(5);
            table.addCell(cell1);
            PdfPCell[] cell2=new PdfPCell[5];
            PdfPRow  row3_2 = new PdfPRow(cell2);
            cell2[0]=new PdfPCell(new Paragraph("漏洞分类",font));
            cell2[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell2[0].setBackgroundColor(new BaseColor(0,255,255));
            cell2[1]=new PdfPCell(new Paragraph("高",font));
            cell2[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell2[1].setBackgroundColor(new BaseColor(0,255,255));
            cell2[2]=new PdfPCell(new Paragraph("中",font));
            cell2[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell2[2].setBackgroundColor(new BaseColor(0,255,255));
            cell2[3]=new PdfPCell(new Paragraph("低",font));
            cell2[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell2[3].setBackgroundColor(new BaseColor(0,255,255));
            cell2[4]=new PdfPCell(new Paragraph("合计",font));
            cell2[4].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cell2[4].setBackgroundColor(new BaseColor(0,255,255));
            listRow.add(row3_2);

            for(PriorityBug priorityBug:BaseInformation.priorityBugs){
                if(!priorityBug.getBugName().equals("nothing")){
                PdfPCell[] cell=new PdfPCell[5];
                PdfPRow  row = new PdfPRow(cell);
                cell[0]=new PdfPCell(new Paragraph(priorityBug.getBugName(),font));
                cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell[1]=new PdfPCell(new Paragraph(priorityBug.getPrioritys().get(1)+"",font));
                cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell[2]=new PdfPCell(new Paragraph(priorityBug.getPrioritys().get(2)+"",font));
                cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell[3]=new PdfPCell(new Paragraph(priorityBug.getPrioritys().get(3)+"",font));
                cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                int total=priorityBug.getPrioritys().get(1)+priorityBug.getPrioritys().get(2)+priorityBug.getPrioritys().get(3);
                cell[4]=new PdfPCell(new Paragraph(total+"",font));
                cell[4].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                listRow.add(row);}
            }
            document.add(table);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveDetailInfo(Document document,Font font){
        try {
            BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese,15,Font.BOLD);
            int i=1;
            document.add(new Paragraph("2. 详细信息",FontChinese));
            for(PriorityBug priorityBug:BaseInformation.priorityBugs){
                document.add(new Paragraph("2."+i+" "+priorityBug.getBugName(),FontChinese));
                i++;
                PdfPTable table = new PdfPTable(1);
                table.setWidthPercentage(100); // 宽度100%填充
                table.setSpacingBefore(10f); // 前间距
                table.setSpacingAfter(10f); // 后间距
              //  List<PdfPRow> listRow = table.getRows();
                table.setSplitLate(false);
                float[] columnWidths = {1.8f};
                table.setWidths(columnWidths);
                int total=priorityBug.getPrioritys().get(1)+priorityBug.getPrioritys().get(2)+priorityBug.getPrioritys().get(3);
                PdfPCell cellNum=new PdfPCell(new Paragraph("漏洞数量："+total,font));
                table.addCell(cellNum);
                PdfPCell cellInfo=new PdfPCell(new Paragraph("漏洞详细信息：",font));
                table.addCell(cellInfo);
                PdfPCell cellInfos=new PdfPCell(new Phrase(priorityBug.getDetailText(),font));
                table.addCell(cellInfos);

                int j=1;
                Set<BugLineAndImage> bugLineAndImages=priorityBug.getBugLineAndImage();
                for(BugLineAndImage bugLineAndImage:bugLineAndImages){
                    PdfPCell cell=new PdfPCell(new Paragraph("漏洞"+j,font));
                    j++;
                    table.addCell(cell);
                    PdfPCell cellLine=new PdfPCell(new Paragraph("漏洞行数："+bugLineAndImage.getBugLine(),font));
                    table.addCell(cellLine);
                    PdfPCell cellImage=new PdfPCell(new Paragraph("漏洞关系图：",font));
                    table.addCell(cellImage);
                    table.addCell(bugLineAndImage.getImages());
                }
                document.add(table);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
