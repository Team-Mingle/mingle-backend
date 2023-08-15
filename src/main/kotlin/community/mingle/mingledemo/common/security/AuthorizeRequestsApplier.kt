package community.mingle.mingledemo.common.security

import org.springframework.security.config.annotation.web.builders.HttpSecurity

typealias AuthorizeRequestsApplier = (HttpSecurity) -> (Unit)

