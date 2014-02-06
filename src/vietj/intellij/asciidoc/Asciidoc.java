/*
 * Copyright 2013 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vietj.intellij.asciidoc;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.AttributesBuilder;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;

import java.util.Collections;
import java.util.Map;

/** @author Julien Viet */
public class Asciidoc {

  /** . */
  private final Asciidoctor doctor;

  public Asciidoc() {
    ClassLoader old = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(AsciidocAction.class.getClassLoader());
      doctor = Asciidoctor.Factory.create();
    }
    finally {
      Thread.currentThread().setContextClassLoader(old);
    }
  }

  public String render(String text) {
    ClassLoader old = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(AsciidocAction.class.getClassLoader());
      String result = "<div id=\"content\">\n" + doctor.render(text, getDefaultOptions()) + "\n</div>";
      return result;
    }
    finally {
      Thread.currentThread().setContextClassLoader(old);
    }
  }

  public Map<String, Object> getDefaultOptions() {
    Attributes attrs = AttributesBuilder.attributes().showTitle(true)
        .sourceHighlighter("coderay").attribute("coderay-css", "style")
        .attribute("env", "idea").attribute("env-idea").get();
    OptionsBuilder opts = OptionsBuilder.options().safe(SafeMode.SAFE).backend("html5").headerFooter(false).attributes(attrs);
    return opts.asMap();
  }
}
