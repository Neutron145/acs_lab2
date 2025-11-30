<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <!-- ВАЖНО: match="/" -->
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Список полётных контроллеров</title>
            </head>
            <body>
                <h1>Список полётных контроллеров</h1>

                <table border="1" cellpadding="4" cellspacing="0">
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th>Производитель</th>
                        <th>Стоимость</th>
                    </tr>

                    <xsl:for-each select="flightControllers/flightController">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="manufacturer"/></td>
                            <td><xsl:value-of select="cost"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <hr/>

                <p>
                    <a href="/api/drones/xml">Список дронов (XML+XSL)</a>
                </p>
                <p>
                    <a href="/api/drones">Список дронов (JSON)</a>
                </p>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
