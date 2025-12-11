<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/flightControllers">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Список полётных контроллеров</title>
            </head>
            <body>
                <h1>Список полётных контроллеров</h1>

                <h2>Добавить контроллер</h2>
                <form action="/ui/flight-controllers/create" method="post">
                    Название:
                    <input type="text" name="name"/><br/>
                    Производитель:
                    <input type="text" name="manufacturer"/><br/>
                    Стоимость:
                    <input type="number" step="0.01" name="cost"/><br/><br/>
                    <button type="submit">Создать</button>
                </form>

                <hr/>

                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th>Производитель</th>
                        <th>Стоимость</th>
                        <th>Действия</th>
                    </tr>

                    <xsl:for-each select="flightController">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="manufacturer"/></td>
                            <td><xsl:value-of select="cost"/></td>
                            <td>

                                <form action="/ui/flight-controllers/update" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="{id}"/>
                                    Назв:
                                    <input type="text" name="name" value="{name}"/>
                                    Произв.:
                                    <input type="text" name="manufacturer" value="{manufacturer}"/>
                                    Стоим.:
                                    <input type="number" step="0.01" name="cost" value="{cost}"/>
                                    <button type="submit">Сохранить</button>
                                </form>

                                <form action="/ui/flight-controllers/delete" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="{id}"/>
                                    <button type="submit">Удалить</button>
                                </form>

                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <br/>

                <p><a href="/api/drones/xml">Список дронов (XML+XSL)</a></p>
                <p><a href="/api/drones">Список дронов (JSON)</a></p>
                <p><a href="/api/change-log/xml">История изменений</a></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
