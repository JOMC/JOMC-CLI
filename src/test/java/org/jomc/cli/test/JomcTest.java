// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
 * Java Object Management and Configuration
 * Copyright (C) Christian Schulte <cs@schulte.it>, 2005-206
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   o Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   o Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * $JOMC$
 *
 */
// </editor-fold>
// SECTION-END
package org.jomc.cli.test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jomc.ObjectManagerFactory;
import org.jomc.cli.Command;
import org.jomc.cli.Jomc;
import org.jomc.model.ModelObject;
import org.jomc.model.Module;
import org.jomc.modlet.ModelContext;
import org.jomc.modlet.ModelContextFactory;
import org.jomc.modlet.Modlet;
import org.jomc.modlet.ModletObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Tests the {@code Jomc} CLI class.
 *
 * <dl>
 *   <dt><b>Identifier:</b></dt><dd>org.jomc.cli.test.JomcTest</dd>
 *   <dt><b>Name:</b></dt><dd>JOMC ⁑ CLI ⁑ Tests ⁑ JomcTest</dd>
 *   <dt><b>Abstract:</b></dt><dd>No</dd>
 *   <dt><b>Final:</b></dt><dd>No</dd>
 *   <dt><b>Stateless:</b></dt><dd>No</dd>
 * </dl>
 *
 * @author <a href="mailto:cs@schulte.it">Christian Schulte</a> 1.0
 * @version 1.10-SNAPSHOT
 */
// </editor-fold>
// SECTION-END
// SECTION-START[Annotations]
// <editor-fold defaultstate="collapsed" desc=" Generated Annotations ">
@javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
// </editor-fold>
// SECTION-END
public class JomcTest
{
    // SECTION-START[JomcTest]

    /**
     * Constant to prefix relative resource names with.
     */
    private static final String ABSOLUTE_RESOURCE_NAME_PREFIX = "/org/jomc/cli/test/";

    /**
     * Constant for the name of the system property holding the output directory for the test.
     */
    private static final String OUTPUT_DIRECTORY_PROPERTY_NAME = "jomc.test.outputDirectory";

    /**
     * Test resources to copy to the resources directory.
     */
    private static final String[] TEST_RESOURCE_NAMES =
    {
        "model-relocations.xsl",
        "modlet-relocations.xsl",
        "jomc.xml",
        "illegal-module.xml",
        "illegal-module-document.xml",
        "module-nonexistent-classes.xml"
    };

    /**
     * The output directory of the instance.
     */
    private File outputDirectory;

    /**
     * Gets the output directory of instance.
     *
     * @return The output directory of instance.
     *
     * @see #setOutputDirectory(java.io.File)
     */
    public final File getOutputDirectory()
    {
        if ( this.outputDirectory == null )
        {
            final String name = System.getProperty( OUTPUT_DIRECTORY_PROPERTY_NAME );
            assertNotNull( "Expected '" + OUTPUT_DIRECTORY_PROPERTY_NAME + "' system property not found.", name );
            this.outputDirectory = new File( new File( name ), "JomcTest" );
            assertTrue( "Expected '" + OUTPUT_DIRECTORY_PROPERTY_NAME + "' system property to hold an absolute path.",
                        this.outputDirectory.isAbsolute() );

            if ( !this.outputDirectory.exists() )
            {
                assertTrue( this.outputDirectory.mkdirs() );
            }
        }

        return this.outputDirectory;
    }

    /**
     * Sets the output directory of instance.
     *
     * @param value The new output directory of instance or {@code null}.
     *
     * @see #getOutputDirectory()
     */
    public final void setOutputDirectory( final File value )
    {
        if ( value != null )
        {
            assertTrue( "Expected absolute 'outputDirectory'.", value.isAbsolute() );
        }

        this.outputDirectory = value;
    }

    @Test
    public final void testNoArguments() throws Exception
    {
        assertEquals( Command.STATUS_FAILURE, Jomc.run( new String[ 0 ] ) );
    }

    @Test
    public final void testGenerateResources() throws Exception
    {
        final File testResourcesDirectory = this.getTestResourcesDirectory();
        assertTrue( testResourcesDirectory.isAbsolute() );

        if ( testResourcesDirectory.exists() )
        {
            FileUtils.deleteDirectory( testResourcesDirectory );
        }

        final String[] help = new String[]
        {
            "generate-resources", "help"
        };

        final String[] args = new String[]
        {
            "generate-resources", "-rd", '"' + this.getTestResourcesDirectory().getAbsolutePath() + '"', "-df",
            '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-D"
        };

        final String[] unsupportedOption = new String[]
        {
            "generate-resources", "--unsupported-option"
        };

        final String[] failOnWarnings = new String[]
        {
            "generate-resources", "-rd", '"' + this.getTestResourcesDirectory().getAbsolutePath() + '"', "-df",
            '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-mn", "DOES_NOT_EXIST", "--fail-on-warnings",
            "-D"
        };

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( help ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( args ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( unsupportedOption ) );

        assertTrue( testResourcesDirectory.mkdirs() );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( args ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( failOnWarnings ) );
    }

    @Test
    public final void testManageSources() throws Exception
    {
        final File testSourcesDirectory = this.getTestSourcesDirectory();
        assertTrue( testSourcesDirectory.isAbsolute() );

        if ( testSourcesDirectory.exists() )
        {
            FileUtils.deleteDirectory( testSourcesDirectory );
        }

        final String[] help = new String[]
        {
            "manage-sources", "help"
        };

        final String[] args = new String[]
        {
            "manage-sources", "-sd", '"' + this.getTestSourcesDirectory().getAbsolutePath() + '"', "-df",
            '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-mn", '"' + this.getTestModuleName() + '"',
            "-D", "-ls", "\r\n", "-idt", "\t", "-tp", "jomc-cli", "-tl",
            '"' + this.getTemplatesDirectory().getAbsolutePath() + '"'
        };

        final String[] unsupportedOption = new String[]
        {
            "manage-sources", "--unsupported-option"
        };

        final String[] failOnWarnings = new String[]
        {
            "manage-sources", "-sd", '"' + this.getTestSourcesDirectory().getAbsolutePath() + '"', "-df",
            '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-mn", "DOES_NOT_EXIST", "--fail-on-warnings",
            "-D", "-tp", "jomc-cli", "-tl", '"' + this.getTemplatesDirectory().getAbsolutePath() + '"'
        };

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( help ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( args ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( unsupportedOption ) );

        assertTrue( testSourcesDirectory.mkdirs() );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( args ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( failOnWarnings ) );
    }

    @Test
    public final void testCommitValidateClasses() throws Exception
    {
        final File testClassesDirectory = this.getTestClassesDirectory();
        assertTrue( testClassesDirectory.isAbsolute() );

        if ( testClassesDirectory.exists() )
        {
            FileUtils.deleteDirectory( testClassesDirectory );
        }

        final String[] commitHelp = new String[]
        {
            "commit-classes", "help"
        };

        final String[] validateHelp = new String[]
        {
            "validate-classes", "help"
        };

        final String[] commitArgs = new String[]
        {
            "commit-classes", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-cd",
            '"' + this.getClassesDirectory().getAbsolutePath() + '"', "-mn",
            '"' + this.getTestModuleName() + '"', "-D"
        };

        final String[] commitArgsNoDirectory = new String[]
        {
            "commit-classes", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-cd",
            '"' + this.getTestClassesDirectory().getAbsolutePath() + '"', "-mn", '"' + this.getTestModuleName() + '"',
            "-D"
        };

        final String[] validateArgs = new String[]
        {
            "validate-classes", "-cp", '"' + this.getClassesDirectory().getAbsolutePath() + '"', "-D"
        };

        final String[] validateArgsNonExistentClasses = new String[]
        {
            "validate-classes", "-df", '"' + this.getTestModelDocumentNonExistentClasses().getAbsolutePath() + '"',
            "-cp", '"' + this.getClassesDirectory().getAbsolutePath() + '"', "-D"
        };

        final String[] commitUnsupportedOption = new String[]
        {
            "commit-classes", "--unsupported-option"
        };

        final String[] validateUnsupportedOption = new String[]
        {
            "validate-classes", "--unsupported-option"
        };

        final String[] commitFailOnWarnings = new String[]
        {
            "commit-classes", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-cd",
            '"' + this.getClassesDirectory().getAbsolutePath() + '"', "-mn", "DOES_NOT_EXIST", "--fail-on-warnings",
            "-D"
        };

        final String[] validateFailOnWarnings = new String[]
        {
            "validate-classes", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-cp",
            '"' + this.getClassesDirectory().getAbsolutePath() + '"', "-mn", "DOES_NOT_EXIST", "--fail-on-warnings",
            "-D"
        };

        final String[] commitWithStylesheet = new String[]
        {
            "commit-classes", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-cd",
            '"' + this.getClassesDirectory().getAbsolutePath() + '"', "-mn", '"' + this.getTestModuleName() + '"',
            "-D", "-stylesheet", '"' + this.getTestModelStylesheet().getAbsolutePath() + '"'
        };

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( commitHelp ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( validateHelp ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( commitArgsNoDirectory ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( commitUnsupportedOption ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( validateUnsupportedOption ) );

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( commitArgs ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( validateArgs ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( commitWithStylesheet ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( commitFailOnWarnings ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( validateFailOnWarnings ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( validateArgsNonExistentClasses ) );
    }

    @Test
    public final void testMergeModules() throws Exception
    {
        final ModelContext context = ModelContextFactory.newInstance().newModelContext();
        final Unmarshaller unmarshaller = context.createUnmarshaller( ModelObject.MODEL_PUBLIC_ID );
        final Schema schema = context.createSchema( ModelObject.MODEL_PUBLIC_ID );
        unmarshaller.setSchema( schema );

        final String[] help = new String[]
        {
            "merge-modules", "help"
        };

        final String[] args = new String[]
        {
            "merge-modules", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-xs",
            '"' + this.getTestModelStylesheet().getAbsolutePath() + '"', "-mn", '"' + this.getTestModuleName() + '"',
            "-d", '"' + this.getTestModelOutputDocument().getAbsolutePath() + '"', "-D"
        };

        final String[] includesArg = new String[]
        {
            "merge-modules", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-xs",
            '"' + this.getTestModelStylesheet().getAbsolutePath() + '"', "-mn", '"' + this.getTestModuleName() + '"',
            "-d", '"' + this.getTestModelOutputDocument().getAbsolutePath() + '"', "-minc=\"JOMC ⁑ CLI\"", "-D"
        };

        final String[] excludesArg = new String[]
        {
            "merge-modules", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-xs",
            '"' + this.getTestModelStylesheet().getAbsolutePath() + '"', "-mn", '"' + this.getTestModuleName() + '"',
            "-d", '"' + this.getTestModelOutputDocument().getAbsolutePath() + '"', "-mexc=\"JOMC ⁑ CLI\"", "-D"
        };

        final String[] unsupportedOption = new String[]
        {
            "merge-modules", "--unsupported-option"
        };

        final String[] illegalDoc = new String[]
        {
            "merge-modules", "-df", '"' + this.getTestModelDocumentIllegalSchemaConstraints().getAbsolutePath() + '"',
            "-xs", '"' + this.getTestModelStylesheet().getAbsolutePath() + '"', "-mn",
            '"' + this.getTestModuleName() + '"', "-d", '"' + this.getTestModelOutputDocument().getAbsolutePath() + '"',
            "-D"
        };

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( help ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( args ) );

        unmarshaller.unmarshal( new StreamSource( this.getTestModelOutputDocument() ), Module.class );

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( includesArg ) );

        final JAXBElement<Module> includedModule =
            unmarshaller.unmarshal( new StreamSource( this.getTestModelOutputDocument() ), Module.class );

        assertNotNull( "Merged module does not contain any included specifications.",
                       includedModule.getValue().getSpecifications() );

        assertNotNull( "Merged module does not contain included 'org.jomc.cli.Command' specification.",
                       includedModule.getValue().getSpecifications().getSpecification( Command.class ) );

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( excludesArg ) );

        final JAXBElement<Module> excludedModule =
            unmarshaller.unmarshal( new StreamSource( this.getTestModelOutputDocument() ), Module.class );

        assertNull( "Merged module contains excluded specifications.",
                    excludedModule.getValue().getSpecifications() );

        assertEquals( Command.STATUS_FAILURE, Jomc.run( unsupportedOption ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( illegalDoc ) );
    }

    @Test
    public final void testValidateModel() throws Exception
    {
        final String[] help = new String[]
        {
            "validate-model", "help"
        };

        final String[] args = new String[]
        {
            "validate-model", "-df", '"' + this.getTestModelDocument().getAbsolutePath() + '"', "-D"
        };

        final String[] unsupportedOption = new String[]
        {
            "validate-model", "--unsupported-option"
        };

        final String[] illegalDoc = new String[]
        {
            "validate-model", "-df", '"' + this.getTestModelDocumentIllegal().getAbsolutePath() + '"', "-D"
        };

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( help ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( args ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( unsupportedOption ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( illegalDoc ) );
    }

    @Test
    public final void testMergeModlets() throws Exception
    {
        final ModelContext context = ModelContextFactory.newInstance().newModelContext();
        final Unmarshaller unmarshaller = context.createUnmarshaller( ModletObject.MODEL_PUBLIC_ID );
        final Schema schema = context.createSchema( ModletObject.MODEL_PUBLIC_ID );
        unmarshaller.setSchema( schema );

        final String[] help = new String[]
        {
            "merge-modlets", "help"
        };

        final String[] args = new String[]
        {
            "merge-modlets", "-xs", '"' + this.getTestModletStylesheet().getAbsolutePath() + '"', "-mdn",
            '"' + this.getTestModletName() + '"', "-d",
            '"' + this.getTestModletOutputDocument().getAbsolutePath() + '"', "-cp", "."
        };

        final String[] includeArgs = new String[]
        {
            "merge-modlets", "-xs", '"' + this.getTestModletStylesheet().getAbsolutePath() + '"', "-mdn",
            '"' + this.getTestModletName() + '"', "-d",
            '"' + this.getTestModletOutputDocument().getAbsolutePath() + '"', "-mdinc=\"JOMC ⁑ Model\"", "-cp", "."
        };

        final String[] excludeArgs = new String[]
        {
            "merge-modlets", "-xs", '"' + this.getTestModletStylesheet().getAbsolutePath() + '"', "-mdn",
            '"' + this.getTestModletName() + '"', "-d",
            '"' + this.getTestModletOutputDocument().getAbsolutePath() + '"',
            "-mdexc=\"JOMC ⁑ Model" + File.pathSeparatorChar + "JOMC Tools ⁑ Tools"
            + File.pathSeparatorChar + "JOMC ⁑ Tools" // < JOMC Tools 1.10
            + File.pathSeparatorChar + "JOMC ⁑ Modlet\"", "-cp", "."
        };

        final String[] unsupportedOption = new String[]
        {
            "merge-modlets", "--unsupported-option"
        };

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( help ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( unsupportedOption ) );

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( args ) );
        Modlet merged = unmarshaller.unmarshal(
            new StreamSource( this.getTestModletOutputDocument() ), Modlet.class ).getValue();

        assertNotNull( merged );
        assertEquals( this.getTestModletName(), merged.getName() );
        assertNotNull( merged.getSchemas() );
        assertNotNull( merged.getServices() );
        assertEquals( 2, merged.getSchemas().getSchema().size() );
        assertEquals( 6, merged.getServices().getService().size() );
        assertFalse( merged.getSchemas().getSchemasByPublicId( new URI( "http://jomc.org/model" ) ).isEmpty() );
        assertFalse( merged.getSchemas().getSchemasByPublicId( new URI( "http://jomc.org/tools/model" ) ).isEmpty() );
        assertNotNull( merged.getSchemas().getSchemaByPublicId( "http://jomc.org/model" ) );
        assertNotNull( merged.getSchemas().getSchemaByPublicId( "http://jomc.org/tools/model" ) );
        assertEquals( 2, merged.getServices().getServices( "org.jomc.modlet.ModelProvider" ).size() );
        assertEquals( 2, merged.getServices().getServices( "org.jomc.modlet.ModelProcessor" ).size() );
        assertEquals( 2, merged.getServices().getServices( "org.jomc.modlet.ModelValidator" ).size() );

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( includeArgs ) );
        merged = unmarshaller.unmarshal(
            new StreamSource( this.getTestModletOutputDocument() ), Modlet.class ).getValue();

        assertNotNull( merged );
        assertEquals( this.getTestModletName(), merged.getName() );
        assertNotNull( merged.getSchemas() );
        assertNotNull( merged.getServices() );
        assertEquals( 1, merged.getSchemas().getSchema().size() );
        assertFalse( merged.getSchemas().getSchemasByPublicId( new URI( "http://jomc.org/model" ) ).isEmpty() );
        assertNotNull( merged.getSchemas().getSchemaByPublicId( "http://jomc.org/model" ) );
        assertEquals( 3, merged.getServices().getService().size() );
        assertEquals( 1, merged.getServices().getServices( "org.jomc.modlet.ModelProvider" ).size() );
        assertEquals( 1, merged.getServices().getServices( "org.jomc.modlet.ModelProcessor" ).size() );
        assertEquals( 1, merged.getServices().getServices( "org.jomc.modlet.ModelValidator" ).size() );

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( excludeArgs ) );
        merged = unmarshaller.unmarshal(
            new StreamSource( this.getTestModletOutputDocument() ), Modlet.class ).getValue();

        assertNotNull( merged );
        assertEquals( this.getTestModletName(), merged.getName() );
        assertNull( merged.getSchemas() );
        assertNull( merged.getServices() );
    }

    @Test
    public final void testShowModel() throws Exception
    {
        final File classesDirectory = new File( this.getOutputDirectory(), "jomc-test-classes" );

        final String[] help = new String[]
        {
            "show-model", "help"
        };

        final String[] showModel = new String[]
        {
            "show-model", "-cp", '"' + classesDirectory.getAbsolutePath() + '"'
        };

        final String[] writeModel = new String[]
        {
            "show-model", "-cp", '"' + classesDirectory.getAbsolutePath() + '"', "-d",
            '"' + this.getTestShowModelOutputDocument().getAbsolutePath() + '"'
        };

        final String[] showSpecification = new String[]
        {
            "show-model", "-cp", '"' + classesDirectory.getAbsolutePath() + '"', "-spec=\"JOMC ⁑ CLI ⁑ Command\""
        };

        final String[] writeSpecification = new String[]
        {
            "show-model", "-cp", '"' + classesDirectory.getAbsolutePath() + '"', "-spec=\"JOMC ⁑ CLI ⁑ Command\"",
            "-d", '"' + this.getTestShowSpecificationOutputDocument().getAbsolutePath() + '"'
        };

        final String[] showInstance = new String[]
        {
            "show-model", "-cp", '"' + classesDirectory.getAbsolutePath() + '"',
            "-impl=\"JOMC ⁑ CLI ⁑ Default show-model Command\""
        };

        final String[] writeInstance = new String[]
        {
            "show-model", "-cp", '"' + classesDirectory.getAbsolutePath() + '"',
            "-impl=\"JOMC ⁑ CLI ⁑ Default show-model Command\"",
            "-d", '"' + this.getTestShowInstanceOutputDocument().getAbsolutePath() + '"'
        };

        final String[] showSpecificationAndInstance = new String[]
        {
            "show-model", "-cp", '"' + classesDirectory.getAbsolutePath() + '"', "-spec=\"JOMC ⁑ CLI ⁑ Command\"",
            "-impl=\"JOMC ⁑ CLI ⁑ Default show-model Command\""
        };

        final String[] writeSpecificationAndInstance = new String[]
        {
            "show-model", "-cp", '"' + classesDirectory.getAbsolutePath() + '"',
            "-spec=\"JOMC ⁑ CLI ⁑ Command\"",
            "-impl=\"JOMC ⁑ CLI ⁑ Default show-model Command\"", "-d",
            '"' + this.getTestShowSpecificationAndInstanceOutputDocument().getAbsolutePath() + '"'
        };

        final String[] unsupportedOption = new String[]
        {
            "show-model", "--unsupported-option"
        };

        assertEquals( Command.STATUS_SUCCESS, Jomc.run( help ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( showModel ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( writeModel ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( showInstance ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( writeInstance ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( showSpecification ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( writeSpecification ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( showSpecificationAndInstance ) );
        assertEquals( Command.STATUS_SUCCESS, Jomc.run( writeSpecificationAndInstance ) );
        assertEquals( Command.STATUS_FAILURE, Jomc.run( unsupportedOption ) );
    }

    @Before
    public void setUp() throws IOException
    {
        // Ensures the singleton is initialized prior to class Jomc switching resource locations.
        ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() );

        final File f = this.getOutputDirectory();
        if ( !f.exists() )
        {
            assertTrue( f.mkdirs() );
        }

        final File resourcesDirectory = this.getResourcesDirectory();
        assertTrue( resourcesDirectory.isAbsolute() );
        FileUtils.deleteDirectory( resourcesDirectory );
        assertTrue( resourcesDirectory.mkdirs() );

        for ( final String testResourceName : TEST_RESOURCE_NAMES )
        {
            final URL rsrc = this.getClass().getResource( ABSOLUTE_RESOURCE_NAME_PREFIX + testResourceName );
            assertNotNull( rsrc );
            FileUtils.copyURLToFile( rsrc, new File( resourcesDirectory, testResourceName ) );
        }

        final File classesDirectory = this.getClassesDirectory();
        this.unzipResource( ABSOLUTE_RESOURCE_NAME_PREFIX + "classfiles.zip", classesDirectory );

        final File templatesDirectory = this.getTemplatesDirectory();
        this.unzipResource( ABSOLUTE_RESOURCE_NAME_PREFIX + "templates.zip", templatesDirectory );
    }

    private void unzipResource( final String resourceName, final File targetDirectory ) throws IOException
    {
        assertTrue( resourceName.startsWith( "/" ) );
        final URL resource = this.getClass().getResource( resourceName );
        assertNotNull( "Expected '" + resourceName + "' not found.", resource );

        assertTrue( targetDirectory.isAbsolute() );
        FileUtils.deleteDirectory( targetDirectory );
        assertTrue( targetDirectory.mkdirs() );

        ZipInputStream in = null;
        boolean suppressExceptionOnClose = true;

        try
        {
            in = new ZipInputStream( resource.openStream() );
            ZipEntry e;

            while ( ( e = in.getNextEntry() ) != null )
            {
                if ( e.isDirectory() )
                {
                    continue;
                }

                final File dest = new File( targetDirectory, e.getName() );
                assertTrue( dest.isAbsolute() );

                OutputStream out = null;

                try
                {
                    out = FileUtils.openOutputStream( dest );
                    IOUtils.copy( in, out );
                    suppressExceptionOnClose = false;
                }
                finally
                {
                    try
                    {
                        if ( out != null )
                        {
                            out.close();
                        }

                        suppressExceptionOnClose = true;
                    }
                    catch ( final IOException ex )
                    {
                        if ( !suppressExceptionOnClose )
                        {
                            throw ex;
                        }
                    }
                }

                in.closeEntry();
            }

            suppressExceptionOnClose = false;
        }
        finally
        {
            try
            {
                if ( in != null )
                {
                    in.close();
                }
            }
            catch ( final IOException e )
            {
                if ( !suppressExceptionOnClose )
                {
                    throw e;
                }
            }
        }
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">
    /** Creates a new {@code JomcTest} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    public JomcTest()
    {
        // SECTION-START[Default Constructor]
        super();
        // SECTION-END
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Dependencies]
    // SECTION-END
    // SECTION-START[Properties]
    // <editor-fold defaultstate="collapsed" desc=" Generated Properties ">
    /**
     * Gets the value of the {@code <Classes Directory>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Directory holding class files.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getClassesDirectory()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Classes Directory" );
        assert _p != null : "'Classes Directory' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Resources Directory>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Directory holding resources.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getResourcesDirectory()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Resources Directory" );
        assert _p != null : "'Resources Directory' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Templates Directory>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Directory holding templates.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTemplatesDirectory()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Templates Directory" );
        assert _p != null : "'Templates Directory' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Classes Directory>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Directory holding class files to commit to and to validate.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestClassesDirectory()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Classes Directory" );
        assert _p != null : "'Test Classes Directory' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Model Document>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Valid model document.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestModelDocument()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Model Document" );
        assert _p != null : "'Test Model Document' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Model Document Illegal>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Model document with invalid model.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestModelDocumentIllegal()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Model Document Illegal" );
        assert _p != null : "'Test Model Document Illegal' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Model Document Illegal Schema Constraints>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Model document not valid to the JOMC JAXP schema.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestModelDocumentIllegalSchemaConstraints()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Model Document Illegal Schema Constraints" );
        assert _p != null : "'Test Model Document Illegal Schema Constraints' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Model Document Non Existent Classes>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Model document referencing non-existent classes.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestModelDocumentNonExistentClasses()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Model Document Non Existent Classes" );
        assert _p != null : "'Test Model Document Non Existent Classes' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Model Output Document>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return File to write a transformed model to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestModelOutputDocument()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Model Output Document" );
        assert _p != null : "'Test Model Output Document' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Model Stylesheet>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Valid model object stylesheet.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestModelStylesheet()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Model Stylesheet" );
        assert _p != null : "'Test Model Stylesheet' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Modlet Name>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Test module name.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.lang.String getTestModletName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Modlet Name" );
        assert _p != null : "'Test Modlet Name' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Modlet Output Document>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return File to write a transformed modlet to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestModletOutputDocument()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Modlet Output Document" );
        assert _p != null : "'Test Modlet Output Document' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Modlet Stylesheet>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Valid modlet object stylesheet.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestModletStylesheet()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Modlet Stylesheet" );
        assert _p != null : "'Test Modlet Stylesheet' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Module Name>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Test module name.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.lang.String getTestModuleName()
    {
        final java.lang.String _p = (java.lang.String) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Module Name" );
        assert _p != null : "'Test Module Name' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Resources Directory>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Directory to generate resources to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestResourcesDirectory()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Resources Directory" );
        assert _p != null : "'Test Resources Directory' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Show Instance Output Document>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return File to write an instance to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestShowInstanceOutputDocument()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Show Instance Output Document" );
        assert _p != null : "'Test Show Instance Output Document' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Show Model Output Document>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return File to write a model to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestShowModelOutputDocument()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Show Model Output Document" );
        assert _p != null : "'Test Show Model Output Document' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Show Specification And Instance Output Document>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return File to write a model holding a specification and an instance to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestShowSpecificationAndInstanceOutputDocument()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Show Specification And Instance Output Document" );
        assert _p != null : "'Test Show Specification And Instance Output Document' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Show Specification Output Document>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return File to write a specification to.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestShowSpecificationOutputDocument()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Show Specification Output Document" );
        assert _p != null : "'Test Show Specification Output Document' property not found.";
        return _p;
    }
    /**
     * Gets the value of the {@code <Test Sources Directory>} property.
     * <p><dl>
     *   <dt><b>Final:</b></dt><dd>No</dd>
     * </dl></p>
     * @return Directory holding source code files to manage.
     * @throws org.jomc.ObjectManagementException if getting the property instance fails.
     */
    @SuppressWarnings({"unchecked", "unused", "PMD.UnnecessaryFullyQualifiedName"})
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.9", comments = "See http://www.jomc.org/jomc/1.9/jomc-tools-1.9" )
    private java.io.File getTestSourcesDirectory()
    {
        final java.io.File _p = (java.io.File) org.jomc.ObjectManagerFactory.getObjectManager( this.getClass().getClassLoader() ).getProperty( this, "Test Sources Directory" );
        assert _p != null : "'Test Sources Directory' property not found.";
        return _p;
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Messages]
    // SECTION-END

}
