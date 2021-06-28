<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<gxl xmlns="http://www.gupro.de/GXL/gxl-1.0.dtd">
    <graph role="graph" edgeids="false" edgemode="directed" id="graph_multiedge">
        <attr name="$version">
            <string>curly</string>
        </attr>
        <node id="n0">
            <attr name="layout">
                <string>288 300 33 18</string>
            </attr>
        </node>
        <node id="n1">
            <attr name="layout">
                <string>427 301 33 18</string>
            </attr>
        </node>
        <node id="n2">
            <attr name="layout">
                <string>290 172 30 18</string>
            </attr>
        </node>
        <node id="n3">
            <attr name="layout">
                <string>422 169 30 18</string>
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
        <edge from="n2" to="n0">
            <attr name="label">
                <string>edge</string>
            </attr>
        </edge>
        <edge from="n2" to="n1">
            <attr name="label">
                <string>edge</string>
            </attr>
            <attr name="layout">
                <string>376 -17 305 181 444 310 11</string>
            </attr>
        </edge>
        <edge from="n3" to="n3">
            <attr name="label">
                <string>type:Edge</string>
            </attr>
        </edge>
        <edge from="n3" to="n1">
            <attr name="label">
                <string>edge</string>
            </attr>
        </edge>
        <edge from="n3" to="n0">
            <attr name="label">
                <string>edge</string>
            </attr>
            <attr name="layout">
                <string>585 -13 424 190 317 297 11</string>
            </attr>
        </edge>
    </graph>
</gxl>
