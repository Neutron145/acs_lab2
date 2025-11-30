<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Список дронов</title>
            </head>
            <body>
                <h1>Список дронов</h1>

                <table border="1" cellpadding="4" cellspacing="0">
                    <tr>
                        <th>ID</th>
                        <th>Тип</th>
                        <th>Контроллер</th>
                    </tr>

                    <xsl:for-each select="/*/*">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="type"/></td>

                            <td>
                                <xsl:choose>
                                    <xsl:when test="controller/name">
                                        <xsl:value-of select="controller/name"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:text>-</xsl:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <hr/>

                <p>
                    <a href="/api/flight-controllers">JSON: список контроллеров (/api/flight-controllers)</a>
                </p>
                <p>
                    <a href="/api/flight-controllers/xml">XML + XSL: список контроллеров (/api/flight-controllers/xml)</a>
                </p>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
