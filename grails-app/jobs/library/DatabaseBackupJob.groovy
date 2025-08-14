package library

class DatabaseBackupJob {

    DatabaseBackupService databaseBackupService

    static triggers = {
        cron name: 'dbBackupTrigger', cronExpression: "0 */10 * * * ?"
    }

    def execute() {
        println "Starting database backup job..."
        databaseBackupService.backupDatabase()
    }
}


