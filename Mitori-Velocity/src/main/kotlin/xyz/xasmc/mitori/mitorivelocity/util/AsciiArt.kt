package xyz.xasmc.mitori.mitorivelocity.util

import org.slf4j.Logger


object AsciiArt {
    fun logAsciiArt(logger: Logger) {
        logger.info("\u001b[36m" + """  __  __ _ _             _    __     __   _            _ _         """ + "\u001b[0m")
        logger.info("\u001b[36m" + """ |  \/  (_) |_ ___  _ __(_)   \ \   / /__| | ___   ___(_) |_ _   _ """ + "\u001b[0m")
        logger.info("\u001b[36m" + """ | |\/| | | __/ _ \| '__| |____\ \ / / _ \ |/ _ \ / __| | __| | | |""" + "\u001b[0m")
        logger.info("\u001b[36m" + """ | |  | | | || (_) | |  | |_____\ V /  __/ | (_) | (__| | |_| |_| |""" + "\u001b[0m")
        logger.info("\u001b[36m" + """ |_|  |_|_|\__\___/|_|  |_|      \_/ \___|_|\___/ \___|_|\__|\__, |""" + "\u001b[0m")
        logger.info("\u001b[36m" + """                                                             |___/ """ + "\u001b[0m")
    }
}