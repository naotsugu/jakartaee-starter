package org.hibernate.jpamodelgen;

import org.hibernate.jpamodelgen.model.MetaAttribute;

public class ClassWriteEnhancer {



    public static String generateEnhanceImports() {
        return """
            import jakarta.persistence.criteria.Expression;
            import jakarta.persistence.criteria.Root;
            import jakarta.persistence.criteria.Join;
            import jakarta.persistence.criteria.Path;
            import jakarta.persistence.criteria.ListJoin;
            import jakarta.persistence.criteria.SetJoin;
            import jakarta.persistence.criteria.MapJoin;
            import java.util.List;
            import java.util.Map;
            import java.util.Set;
            import java.util.function.Supplier;
            """;
    }


    protected static String generateEnhanceMethod(MetaAttribute metaMember) {
        StringBuilder sb = new StringBuilder();
        sb.append("//    " + metaMember.getAttributeDeclarationString()).append("\n");
        sb.append("//    " + metaMember.getAttributeNameDeclarationString()).append("\n");
        sb.append("//    " + metaMember.getMetaType()).append("\n");
        sb.append("//    " + metaMember.getPropertyName()).append("\n");
        sb.append("//    " + metaMember.getTypeDeclaration()).append("\n");
        sb.append("//    " + metaMember.getHostingEntity()).append("\n");
        return sb.toString();
    }
}
