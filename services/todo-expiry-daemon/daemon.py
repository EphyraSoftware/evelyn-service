import logging
from datetime import datetime

import pymongo


def run():
    client = pymongo.MongoClient('localhost', 9081)
    db = client.evelyn
    collection = db.todoModel

    logging.debug('Connected to MongoDB, starting expiry query.')

    delete_result = collection.delete_many({"expiry": {"$lt": datetime.now()}})

    logging.info("Deleted [%s] expired todos", delete_result.deleted_count)
