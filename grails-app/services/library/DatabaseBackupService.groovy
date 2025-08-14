package library

import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class DatabaseBackupService {

    def grailsApplication

    void backupDatabase() {

        log.info "Starting database backup..."

        def backupDir = grailsApplication.config.getProperty('backup.directory', String)
        def filenamePattern = grailsApplication.config.getProperty('backup.filenamePattern', String)

        def dbUsername= grailsApplication.config.getProperty('backup.db.username', String)
        def dbPassword= grailsApplication.config.getProperty('backup.db.password', String)
        def dbName= grailsApplication.config.getProperty('backup.db.name', String)
        def dbHost= grailsApplication.config.getProperty('backup.db.host', String)
        def dbPort= grailsApplication.config.getProperty('backup.db.port', String)
        def mysqldump= grailsApplication.config.getProperty('backup.mysqldumpPath', String)

        def dateFormatter = new SimpleDateFormat('yyyyMMdd_HHmmss')
        def dateStr = dateFormatter.format(new Date())
        def filename = filenamePattern.replace('${date}', dateStr)

        def backupFilePath = new File(backupDir, filename).absolutePath

        try {
            new File(backupDir).mkdirs()

            def command = [
                    mysqldump,
                    "-h$dbHost",
                    "-P$dbPort",
                    "-u$dbUsername",
                    "--password=$dbPassword",
                    dbName,
                    "-r", backupFilePath
            ]

            println "Executing...."

            def process = command.execute()
            def err = new StringBuffer()
            process.waitForProcessOutput(System.out, err)

            if (process.exitValue() == 0) {
                println "Backup successful: $backupFilePath"
            } else {
                println "Backup failed: $err"
            }

        } catch (Exception e) {
            println "Exception during backup: ${e.message}", e
        }
    }

}
