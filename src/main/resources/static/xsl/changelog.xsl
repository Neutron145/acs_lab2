<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/changeLogs">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>История изменений</title>
            </head>
            <body>
                <h1>История изменений</h1>

                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Сущность</th>
                        <th>ID сущности</th>
                        <th>Операция</th>
                        <th>Время</th>
                        <th>Детали</th>
                    </tr>

                    <xsl:for-each select="changeLog">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="entityName"/></td>
                            <td><xsl:value-of select="entityId"/></td>
                            <td><xsl:value-of select="operation"/></td>
                            <td><xsl:value-of select="changeTime"/></td>
                            <td><xsl:value-of select="details"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <br/>

                <p><a href="/api/drones/xml">Список дронов (XML+XSL)</a></p>
                <p><a href="/api/flight-controllers/xml">Список полётных контроллеров (XML+XSL)</a></p>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
