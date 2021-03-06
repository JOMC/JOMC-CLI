<?xml version="1.0" encoding="UTF-8"?>
<!--

  Copyright (C) Christian Schulte <cs@schulte.it>, 2005-206
  All rights reserved.

  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions
  are met:

    o Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.

    o Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in
      the documentation and/or other materials provided with the
      distribution.

  THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
  AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
  THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY DIRECT, INDIRECT,
  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

  $JOMC$

-->
<xsl:stylesheet xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:jomc="http://jomc.org/model"
                xmlns:modlet="http://jomc.org/modlet"
                version="2.0">

  <xsl:output method="xml" indent="yes" omit-xml-declaration="no"
              encoding="UTF-8" standalone="no"/>

  <xsl:param name="jomc.test.resourceEncoding" required="yes"/>
  <xsl:param name="jomc.test.outputDirectory" required="yes"/>
  <xsl:param name="file.separator" required="yes"/>

  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="jomc:properties/jomc:property/@value">
    <xsl:variable name="value" select="string(.)"/>
    <xsl:attribute name="{name()}">
      <xsl:choose>
        <xsl:when test="starts-with($value, '${jomc.test.resourceEncoding}')">
          <xsl:value-of select="translate(concat($jomc.test.resourceEncoding, substring-after($value, '${jomc.test.resourceEncoding}')), '/', $file.separator)"/>
        </xsl:when>
        <xsl:when test="starts-with($value, '${jomc.test.outputDirectory}')">
          <xsl:value-of select="translate(concat($jomc.test.outputDirectory, substring-after($value, '${jomc.test.outputDirectory}')), '/', $file.separator)"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="."/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:attribute>
  </xsl:template>

</xsl:stylesheet>
