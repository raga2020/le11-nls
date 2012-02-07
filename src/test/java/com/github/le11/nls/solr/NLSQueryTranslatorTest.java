/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.github.le11.nls.solr;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author tommaso
 */
public class NLSQueryTranslatorTest {

  @Test
  public void testTranslation() {
    String[] testQueries = new String[]{"articles about science", "people working at CNR", "the history of Albert Einstein",
            "back to the future"};
    try {
      NLSQueryTranslator nlsQueryTranslator = new NLSQueryTranslator();
      NLSQueryAnalyzer nlsQueryAnalyzer = mock(NLSQueryAnalyzer.class);
      when(nlsQueryAnalyzer.isNLSQuery("")).thenReturn(true);
      when(nlsQueryAnalyzer.extractConcepts()).thenReturn(new String[]{"science"});
      for (String nlsQuery : testQueries) {
        when(nlsQueryAnalyzer.expandBoosts()).thenReturn(nlsQuery + "^5.0");
        String explicitNLSQuery = nlsQueryTranslator.createNLSExplicitQueryString(nlsQuery, nlsQueryAnalyzer);
        System.out.println(explicitNLSQuery);
        assertNotNull("returning a null query is wrong", explicitNLSQuery);
        String[] clauses = explicitNLSQuery.split(" \\(");
        assertTrue(clauses.length == 2);
      }
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

}
