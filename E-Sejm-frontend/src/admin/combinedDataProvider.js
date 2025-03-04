import userDataProvider from "./providers/userDataProvider.js";
import newsDataProvider from "./providers/newsDataProvider.js";


const combinedDataProvider = {
    getList: (resource, params) => {
        if (resource === 'users') {
            return userDataProvider.getList(resource, params);
        } else if (resource === 'news') {
            return newsDataProvider.getList(resource, params);
        }
        throw new Error(`Resource ${resource} not supported.`);
    },
    getOne: (resource, params) => {
        if (resource === 'users') {
            return userDataProvider.getOne(resource, params);
        } else if (resource === 'news') {
            return newsDataProvider.getOne(resource, params);
        }
        throw new Error(`Resource ${resource} not supported.`);
    },
    create: (resource, params) => {
        if (resource === 'news') {
            return newsDataProvider.create(resource, params);
        }

        throw new Error(`Resource ${resource} not supported.`);
    },
    update: (resource, params) => {
        if (resource === 'users') {
            return userDataProvider.update(resource, params);
        } else if (resource === 'news') {
            return newsDataProvider.update(resource, params);
        }
        throw new Error(`Resource ${resource} not supported.`);
    },
    delete: (resource, params) => {
        if (resource === 'users') {
            return userDataProvider.delete(resource, params);
        } else if (resource === 'news') {
            return newsDataProvider.delete(resource, params);
        }
        throw new Error(`Resource ${resource} not supported.`);
    },
};

export default combinedDataProvider;