package org.example;



import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.nio.file.Path;

@Mojo(name = "instrument", defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class ASMInsertMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File projectDir;   // use File, not String

    @Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true)
    private File classesDir;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Running ASM transformer...");

        // Pass absolute paths to ASMInsertExample
        try {
            ASMInsertExample.main(new String[]{
                    projectDir.getAbsolutePath(),
                    classesDir.getAbsolutePath()
            });
        } catch (Exception e) {
            throw new MojoExecutionException("ASM transformation failed", e);
        }
    }
}

