package library

import grails.plugin.springsecurity.annotation.Secured
import java.text.SimpleDateFormat

@Secured(['ROLE_LIBRARIAN'])
class ReportController {

    def bookService

    def exportExcel() {
        def workbook = bookService.generateFullWorkbook()

        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        def now = new Date()
        def sdf = new SimpleDateFormat("yyyyMMdd_HHmmss")
        def formatted = sdf.format(now)

        def filename = "library_books_report_${formatted}.xlsx"
        response.setHeader("Content-Disposition", "attachment; filename=${filename}")

        workbook.write(response.outputStream)
        workbook.close()
        response.outputStream.flush()
    }
}

