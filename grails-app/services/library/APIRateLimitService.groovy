package library

import grails.gorm.transactions.Transactional

@Transactional
class APIRateLimitService {

    Map<String, List<Long>> userRequests = [:].withDefault { [] }

    int USER_LIMIT = 5
    long WINDOW = 60 * 1000

    boolean allowRequest(String userId) {
        long now = System.currentTimeMillis()

        userRequests[userId] = userRequests[userId].findAll { now - it < WINDOW }

        if (userRequests[userId].size() >= USER_LIMIT) return false

        userRequests[userId] << now
        return true
    }

    Map<String, Integer> remaining(String userId) {
        long now = System.currentTimeMillis()
        [
                user: USER_LIMIT - userRequests[userId].findAll { now - it < WINDOW }.size()
        ]
    }
}
