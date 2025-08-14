

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'library.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'library.UserRole'
grails.plugin.springsecurity.authority.className = 'library.Role'
grails.plugin.springsecurity.password.algorithm = 'bcrypt'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['IS_AUTHENTICATED_FULLY']],
	[pattern: '/**',             access: ['IS_AUTHENTICATED_FULLY']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['IS_AUTHENTICATED_FULLY']],
	[pattern: '/index.gsp',      access: ['IS_AUTHENTICATED_FULLY']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
	[pattern: '/register/**',    access: ['permitAll']],
	[pattern: '/register',       access: ['permitAll']],
	[pattern: '/library/findBookByTitle',       access: ['ROLE_STUDENT', 'ROLE_LIBRARIAN']]
	//[pattern: '/api/books/**', access: ['permitAll']],
	//[pattern: '/api/**', access: ['permitAll']], // Optional broader rule
	//[pattern: '/**',          access: ['IS_AUTHENTICATED_ANONYMOUSLY']] // fallback
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
	//[pattern: '/api/**', filters: 'none'],
	//[pattern: '/**', filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/'

grails.servlet.multipart.enabled = true
grails.servlet.multipart.maxFileSize = 5 * 1024 * 1024
grails.servlet.multipart.maxRequestSize = 10 * 1024 * 1024
