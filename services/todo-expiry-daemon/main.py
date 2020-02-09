import logging
from logging.config import fileConfig

import daemon

fileConfig('logging-config.ini')

if __name__ == '__main__':
    logging.info('Expiry daemon launching.')
    daemon.run()
