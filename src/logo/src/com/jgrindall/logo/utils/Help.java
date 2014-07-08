/**
 * Help.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Contents of help file.
 */
package com.jgrindall.logo.utils;
public class Help{
    public static String getMessage(){
        return "<html>" +
                "<p><b>Logo syntax</b></p>" +
                "<table border='1' cellpadding='5'>"+

                "<tr bgcolor='#5E5E5E'>" +
                "<td>fd 100</td>" +
                "<td>Move forward 100</td>" +
                "</tr>" +

                "<tr>" +
                "<td>rt 90</td>" +
                "<td>Turn right 90°</td>" +
                "</tr>" +

                "<tr bgcolor='#5E5E5E'>" +
                "<td>rpt 6 [fd 100]</td>" +
                "<td>Repeat 6 times: 'fd 100'</td>" +
                "</tr>" +

                "<tr>" +
                "<td>make :a 6</td>" +
                "<td><p>Set variable :a equal to 6</p><p>Variable names must consist of letters only, (no numbers)</p></p>and must begin with a colon (:)</p></td>" +
                "</tr>" +

                "<tr bgcolor='#5E5E5E'>" +
                "<td><p>proc drawTri()</p><p>rpt 3[fd 100 rt 120]</p><p>end</p><p>drawTri()</p></td>" +
                "<td></p>A procedure called drawTri, with no input, is defined and then called.</p><p>Procedure names must consist of letters only.</p></td>" +
                "</tr>" +

                "<tr>" +
                "<td><p>proc drawTri(:sideLength)</p><p>rpt 3[fd :sideLength rt 120]</p><p>end</p><p>drawTri(50)</p></td>" +
                "<td></p>A procedure called drawTri, with one input (:sideLength)</p><p>is defined and then called. A triangle of side length 50 is drawn.</p></td>" +
                "</tr>" +

                "<tr bgcolor='#5E5E5E'>" +
                "<td><p>proc drawPoly(:s,:n)</p><p>rpt :n[fd :s rt (360/:n)]</p><p>end</p><p>drawPoly(75,5)</p></td>" +
                "<td></p>a procedure called drawPoly, with two inputs, :s and :n.</p><p>A pentagon of side length 75 is drawn.</p></td>" +
                "</tr>" +

                "</table>"+
                "</html>";
    }
}