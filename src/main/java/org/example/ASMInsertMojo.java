package org.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.nio.file.Path;

/**
 * Maven plugin Mojo to run ASM transformations after classes are compiled.
 */
@Mojo(name = "insert", defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class ASMInsertMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true)
    private File classesDir;

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File projectDir;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Running ASM transformer...");

        try {
            Path projectPath = projectDir.toPath();
            Path classesPath = classesDir.toPath();

            getLog().info("Classes output directory: " + classesPath);

            // Call ASM transformer
            ASMInsertExample.run(projectPath, classesPath);

        } catch (Exception e) {
            throw new MojoExecutionException("ASM transformation failed", e);
        }
    }
}