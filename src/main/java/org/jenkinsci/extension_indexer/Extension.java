package org.jenkinsci.extension_indexer;

import com.sun.source.util.JavacTask;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import net.sf.json.JSONObject;
import org.jvnet.hudson.update_center.MavenArtifact;

import javax.lang.model.element.TypeElement;

/**
 * Information about the implementation of an extension point
 * (and extension point definition.)
 *
 * @author Kohsuke Kawaguchi
 * @see ExtensionSummary
 */
public final class Extension extends BaseClass {

    Extension(MavenArtifact artifact, JavacTask javac, Trees trees, TypeElement implementation, TreePath implPath, TypeElement extensionPoint) {
        super(artifact, javac, trees, implementation, implPath, extensionPoint);
    }

    /**
     * Returns true if this record is about a definition of an extension point
     * (as opposed to an implementation of a defined extension point.)
     */
    public boolean isDefinition() {
        return baseType!= null && implementation.equals(baseType);
    }

    /**
     * Gets the information captured in this object as JSON.
     */
    public JSONObject toJSON() {
        JSONObject i = super.toJSON();
        if (!isDefinition() && baseType != null)
            i.put("extensionPoint",baseType.getQualifiedName().toString());
        return i;
    }

}
