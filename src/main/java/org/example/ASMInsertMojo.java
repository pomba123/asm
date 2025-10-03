package org.example;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "instrument", defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class ASMInsertMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File projectDir;

    @Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true)
    private File classesDir;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Running ASM transformer...");

        try {
            // Build user classloader (target/classes + dependencies)
            List<URL> urls = new ArrayList<>();

            // add target/classes
            urls.add(classesDir.toURI().toURL());

            // add all compile/runtime dependencies
            for (Artifact artifact : project.getArtifacts()) {
                if (artifact.getFile() != null) {
                    urls.add(artifact.getFile().toURI().toURL());
                }
            }

            URLClassLoader userClassLoader =
                    new URLClassLoader(urls.toArray(new URL[0]),
                            Thread.currentThread().getContextClassLoader());

            getLog().info("User classloader built with " + urls.size() + " entries");

            // Call your ASM entrypoint, passing userClassLoader
            ASMInsertExample.run(
                    projectDir.toPath(),
                    classesDir.toPath(),
                    userClassLoader
            );

        } catch (Exception e) {
            throw new MojoExecutionException("ASM transformation failed", e);
        }
    }
}
