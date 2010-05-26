package org.webfabric.sitemesh.v3

import org.sitemesh.builder.SiteMeshFilterBuilder
import org.sitemesh.content.tagrules.html.DivExtractingTagRuleBundle

class ConfigurableSiteMeshFilter extends org.sitemesh.config.ConfigurableSiteMeshFilter {
  override def applyCustomConfiguration(builder: SiteMeshFilterBuilder) = {
    builder.setCustomDecoratorSelector(new ListOfDecoratorSelectors(new QueryStringDecoratorSelector(), builder.getDecoratorSelector))
    builder.addTagRuleBundle(new DivExtractingTagRuleBundle)
  }
}