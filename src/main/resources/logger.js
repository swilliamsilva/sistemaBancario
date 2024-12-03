// logger.js
const Logger = {
    info: (message) => {
        console.info(`INFO: ${new Date().toISOString()} - ${message}`);
    },
    debug: (message) => {
        console.debug(`DEBUG: ${new Date().toISOString()} - ${message}`);
    },
    error: (message) => {
        console.error(`ERROR: ${new Date().toISOString()} - ${message}`);
    }
};

export default Logger;
