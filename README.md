This project has been replaced with [UtterlyIdle](http://code.google.com/p/utterlyidle/).


WebFabric is not a framework or a single library but a suite of libraries, tools and concepts used to create web applications.

This green field implementation is in it's early days but the ideas have all been tried and tested on large scale commercial projects on both the Java and .Net stacks.

Current features:
  * SiteMesh extensions
    * SiteMeshExtendedPageParser
    * SiteMeshStringTemplateDecorators
  * StringTemplate extensions
    * LoadingStringTemplatesFromUrl
    * ServerSideIncludesInStringTemplate
  * Http Filters
    * EtagSupport
    * CacheControl
    * NoSession
  * ClientSideIncludes

Upcoming features:
  * XSRF protection
  * GZIP support that works with ETags