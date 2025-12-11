<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/drones">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Список дронов</title>
            </head>
            <body>
                <h1>Список дронов</h1>

                <h2>Добавить дрон</h2>
                <form action="/ui/drones/create" method="post">
                    Тип:
                    <input type="text" name="type"/><br/>
                    ID контроллера:
                    <input type="number" name="controllerId"/><br/><br/>
                    <button type="submit">Создать</button>
                </form>

                <hr/>

                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Тип</th>
                        <th>ID контроллера</th>
                        <th>Название контроллера</th>
                        <th>Действия</th>
                    </tr>

                    <xsl:for-each select="drone">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="type"/></td>
                            <td><xsl:value-of select="controller/id"/></td>
                            <td><xsl:value-of select="controller/name"/></td>
                            <td>

                                <form action="/ui/drones/update" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="{id}"/>
                                    Тип:
                                    <input type="text" name="type" value="{type}"/>
                                    ID контроллера:
                                    <input type="number" name="controllerId" value="{controller/id}"/>
                                    <button type="submit">Сохранить</button>
                                </form>

                                <form action="/ui/drones/delete" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="{id}"/>
                                    <button type="submit">Удалить</button>
                                </form>

                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <br/>

                <p><a href="/api/flight-controllers/xml">Список полётных контроллеров (XML+XSL)</a></p>
                <p><a href="/api/flight-controllers">Список полётных контроллеров (JSON)</a></p>
                <p><a href="/api/change-log/xml">История изменений</a></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
