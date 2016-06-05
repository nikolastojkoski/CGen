package com.company;

import java.io.FileWriter;

/**
 * Created by Nikola on 5/9/2016.
 */
public class HtmlGenerator {

    private String imageLink;
    private String downloadLink;
    private String youtubeID;
    private String wikipediaSummary;

    private String html;

    public HtmlGenerator(){}
    public void setImageLink(String imageLink)               { this.imageLink = imageLink;}
    public void setDownloadLink(String downloadLink)         { this.downloadLink = downloadLink; }
    public void setYoutubeID(String youtubeID)               { this.youtubeID = youtubeID; }
    public void setWikipediaSummary(String wikipediaSummary) { this.wikipediaSummary = wikipediaSummary; }

    public String getHtml()
    {
        return html;
    }
    public void generate()
    {
        html = "<div class=\"separator\" style=\"clear: both; text-align: center;\">\n" +
                "<br /></div>\n" +
                "<div class=\"separator\" style=\"clear: both; text-align: center;\">\n" +
                "<img border=\"0\" height=\"360\" src=\"";
        html += imageLink;
        html += "\" alt = \"mainImage\" width=\"640\" /></a></div>\n" +
                "<div class=\"separator\" style=\"clear: both; text-align: center;\">\n" +
                "</div>\n" +
                "<div class=\"separator\" style=\"clear: both; text-align: center;\">\n" +
                "</div>\n" +
                "<h2 style=\"clear: both; text-align: center;\">\n" +
                "</h2>\n" +
                "<div style=\"text-align: center;\">\n" +
                "<span style=\"font-family: &quot;trebuchet ms&quot; , sans-serif; font-size: x-large;\"><b><u>Download the full game for free</u></b></span><br />\n" +
                "<span style=\"font-family: &quot;trebuchet ms&quot; , sans-serif; font-size: large;\"><b><u>(Extract .rar arcive and run Setup.exe)</u></b></span></div>\n" +
                "<div class=\"\" style=\"clear: both; text-align: center;\">\n" +
                "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"tr-caption-container\" style=\"margin-left: auto; margin-right: auto; text-align: center;\"><tbody>\n" +
                "<tr><td style=\"text-align: center;\"><span style=\"margin-left: auto; margin-right: auto;\"><a href=\"";
        html += downloadLink;
        html += "\" rel=\"nofollow\"><img border=\"0\" height=\"71\" src=\"https://2.bp.blogspot.com/-1Xw3qEk7dpA/Vcm0oEIZsII/AAAAAAAAAEw/ftof5O5ZXgk/s200/Download.png\" width=\"200\" /></a></span></td></tr>\n" +
                "<tr><td class=\"tr-caption\" style=\"text-align: center;\"><a href=\"";
        html += downloadLink;
        html += "\" rel=\"nofollow\">For Windows XP,Vista,7,8,10</a></td></tr>\n" +
                "</tbody></table>\n" +
                "<div class=\"separator\" style=\"clear: both; text-align: center;\">\n" +
                "<br /></div>\n" +
                "<div style=\"clear: both; text-align: center;\">\n" +
                "<span style=\"font-family: &quot;trebuchet ms&quot; , sans-serif; font-size: large;\"><b><span style=\"font-family: &quot;trebuchet ms&quot; , sans-serif; font-size: x-large;\"><u>Minimum System Requirements</u></span></b></span></div>\n" +
                "</div>\n" +
                "<table class=\"req-table\" style=\"background-color: white; border-collapse: collapse; border-spacing: 0px; border-style: none; color: #222222; cursor: auto; font-family: &quot;verdana&quot; , &quot;arial&quot; , sans-serif; font-size: 12.7160005569458px; margin-left: 50px; padding: 0px; text-align: center; width: 570px;\"><tbody style=\"cursor: auto;\">\n" +
                "<tr style=\"background-color: #eceef5; cursor: auto;\"><th style=\"border: 0px; cursor: auto; font-family: inherit; font-size: inherit; font-stretch: inherit; font-style: inherit; font-variant: inherit; line-height: inherit; margin-left: 50px; padding: 0.5em;\" width=\"125\">CPU:</th><td style=\"border: 0px solid rgb(0, 0, 0); cursor: auto; margin-left: 50px; padding: 0.5em;\" width=\"380\">Intel Core 2 Duo E6600 or AMD Phenom X3 8750</td></tr>\n" +
                "</tbody></table>\n" +
                "<table class=\"req-table\" style=\"background-color: white; border-collapse: collapse; border-spacing: 0px; border-style: none; color: #222222; cursor: auto; font-family: Verdana, Arial, sans-serif; font-size: 12.7160005569458px; margin-left: 50px; padding: 0px; text-align: center; width: 570px;\"><tbody style=\"cursor: auto;\">\n" +
                "<tr style=\"cursor: auto;\"><th style=\"border: 0px; cursor: auto; font-family: inherit; font-size: inherit; font-stretch: inherit; font-style: inherit; font-variant: inherit; line-height: inherit; margin-left: 50px; padding: 0.5em;\" width=\"125\">RAM:</th><td style=\"border: 0px solid rgb(0, 0, 0); cursor: auto; margin-left: 50px; padding: 0.5em;\" width=\"380\">2 GB</td></tr>\n" +
                "</tbody></table>\n" +
                "<table class=\"req-table\" style=\"background-color: white; border-collapse: collapse; border-spacing: 0px; border-style: none; color: #222222; cursor: auto; font-family: Verdana, Arial, sans-serif; font-size: 12.7160005569458px; margin-left: 50px; padding: 0px; text-align: center; width: 570px;\"><tbody style=\"cursor: auto;\">\n" +
                "<tr style=\"background-color: #eceef5; cursor: auto;\"><th style=\"border: 0px; cursor: auto; font-family: inherit; font-size: inherit; font-stretch: inherit; font-style: inherit; font-variant: inherit; line-height: inherit; margin-left: 50px; padding: 0.5em;\" width=\"125\">OS:</th><td style=\"border: 0px solid rgb(0, 0, 0); cursor: auto; margin-left: 50px; padding: 0.5em;\" width=\"380\">Windows 7/Vista/XP</td></tr>\n" +
                "</tbody></table>\n" +
                "<table class=\"req-table\" style=\"background-color: white; border-collapse: collapse; border-spacing: 0px; border-style: none; color: #222222; cursor: auto; font-family: Verdana, Arial, sans-serif; font-size: 12.7160005569458px; margin-left: 50px; padding: 0px; text-align: center; width: 570px;\"><tbody style=\"cursor: auto;\">\n" +
                "<tr style=\"cursor: auto;\"><th style=\"border: 0px; cursor: auto; font-family: inherit; font-size: inherit; font-stretch: inherit; font-style: inherit; font-variant: inherit; line-height: inherit; margin-left: 50px; padding: 0.5em;\" width=\"125\">Video Card:</th><td style=\"border: 0px solid rgb(0, 0, 0); cursor: auto; margin-left: 50px; padding: 0.5em;\" width=\"380\">GeForce GT 420 or Radeon HD 6450</td></tr>\n" +
                "</tbody></table>\n" +
                "<table class=\"req-table\" style=\"background-color: white; border-collapse: collapse; border-spacing: 0px; border-style: none; color: #222222; cursor: auto; font-family: Verdana, Arial, sans-serif; font-size: 12.7160005569458px; margin-left: 50px; padding: 0px; text-align: center; width: 570px;\"><tbody style=\"cursor: auto;\">\n" +
                "<tr style=\"background-color: #eceef5; cursor: auto;\"><th style=\"border: 0px; cursor: auto; font-family: inherit; font-size: inherit; font-stretch: inherit; font-style: inherit; font-variant: inherit; line-height: inherit; margin-left: 50px; padding: 0.5em;\" width=\"125\">Free Disk Space:</th><td style=\"border: 0px solid rgb(0, 0, 0); cursor: auto; margin-left: 50px; padding: 0.5em;\" width=\"380\">4 GB for minimal install</td></tr>\n" +
                "</tbody></table>\n" +
                "<br />\n" +
                "<div class=\"separator\" style=\"clear: both; text-align: center;\">\n" +
                "<iframe height=\"315\" src=\"http://www.youtube.com/embed/";
        html += youtubeID;
        html += "?autoplay=0\" style=\"marginwidth: 20px;\" width=\"560\">\n" +
                "</iframe></div>\n" +
                "<div class=\"separator\" style=\"clear: both; text-align: center;\">\n" +
                "<br /></div>\n" +
                "<blockquote class=\"tr_bq\" style=\"clear: both; text-align: center;\">\n" +
                "<div style=\"text-align: justify;\">";
        html += wikipediaSummary;
        html += "</div>\n" +
                "</blockquote>\n" +
                "<br />\n" +
                "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"tr-caption-container\" style=\"margin-left: auto; margin-right: auto; text-align: center;\"><tbody>\n" +
                "<tr><td style=\"text-align: center;\"><img alt=\"\" border=\"0\" height=\"42\" src=\"https://1.bp.blogspot.com/-rz70mo93tYw/Vq1mciTWNCI/AAAAAAAAAN4/o8vMp7-YyKY/s200/intelposts120326_starstruck_560.jpg\" title=\"Rating is unlocked after downloading\" width=\"200\" /></td></tr>\n" +
                "<tr><td class=\"tr-caption\" style=\"text-align: center;\"><span style=\"font-family: &quot;trebuchet ms&quot; , sans-serif; font-size: small;\">Average user rating: <b>4.2</b> &nbsp;(out of &nbsp;<b>1284</b> votes)</span><br />\n" +
                "<span style=\"font-family: &quot;trebuchet ms&quot; , sans-serif; font-size: x-small;\"><i>*Rating is unlocked after downloading</i></span></td></tr>\n" +
                "</tbody></table>\n" +
                "<br />\n" +
                "<div style=\"text-align: center;\">\n" +
                "<br /></div>\n" +
                "<br />";
    }
    public void saveHtmlFile(String outputNameDir)
    {
        Utils.saveFile(outputNameDir, html);
    }

}
