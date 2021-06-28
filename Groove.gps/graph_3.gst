<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<gxl xmlns="http://www.gupro.de/GXL/gxl-1.0.dtd">
    <graph role="graph" edgeids="false" edgemode="directed" id="graph_3">
        <attr name="$version">
            <string>curly</string>
        </attr>
        <node id="n0">
            <attr name="layout">
                <string>274 384 33 18</string>
            </attr>
        </node>
        <node id="n1">
            <attr name="layout">
                <string>396 381 33 18</string>
            </attr>
        </node>
        <node id="n2">
            <attr name="layout">
                <string>330 241 62 36</string>
            </attr>
        </node>
        <edge from="n0" to="n0">
            <attr name="label">
                <string>type:Node</string>
            </attr>
        </edge>
        <edge from="n1" to="n1">
            <attr name="label">
                <string>type:Node</string>
            </attr>
        </edge>
        <edge from="n2" to="n2">
            <attr name="label">
                <string>type:Edge</string>
            </attr>
        </edge>
        <edge from="n2" to="n2">
            <attr name="label">
                <string>let:weight = 1</string>
            </attr>
        </edge>
        <edge from="n2" to="n0">
            <attr name="label">
                <string>edge</string>
            </attr>
        </edge>
        <edge from="n2" to="n1">
            <attr name="label">
                <string>edge</string>
            </attr>
        </edge>
    </graph>
</gxl>
