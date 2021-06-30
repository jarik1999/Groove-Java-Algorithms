<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<gxl xmlns="http://www.gupro.de/GXL/gxl-1.0.dtd">
    <graph role="graph" edgeids="false" edgemode="directed" id="s6">
        <node id="n2"/>
        <node id="n0"/>
        <node id="n1"/>
        <node id="n5"/>
        <edge from="n2" to="n2">
            <attr name="label">
                <string>type:Edge</string>
            </attr>
        </edge>
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
        <edge from="n5" to="n5">
            <attr name="label">
                <string>type:Result</string>
            </attr>
        </edge>
        <edge from="n2" to="n2">
            <attr name="label">
                <string>let:weight = int:1</string>
            </attr>
        </edge>
        <edge from="n2" to="n1">
            <attr name="label">
                <string>edge</string>
            </attr>
        </edge>
        <edge from="n2" to="n0">
            <attr name="label">
                <string>edge</string>
            </attr>
        </edge>
        <edge from="n5" to="n5">
            <attr name="label">
                <string>flag:done</string>
            </attr>
        </edge>
        <edge from="n5" to="n5">
            <attr name="label">
                <string>let:success = bool:false</string>
            </attr>
        </edge>
    </graph>
</gxl>
