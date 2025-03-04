import {store} from "../../redux/store.js";
import {
    createNewsAction,
    deleteNewsAction,
    getNewsListAction, updateNewsAction,
    uploadNewsPhotoAction
} from "../../redux/NewsService/Action.js";

const userData = (() => { try { return JSON.parse(localStorage.getItem("user")) || null; } catch { return null; } })();

const newsDataProvider = {
    getList: async () => {
        await store.dispatch(getNewsListAction());
        const state = store.getState();
        const newsState = state.news;
        const newsList = newsState.newsList || [];
        return {
            data: newsList.map(news => ({
                id: news.id,
                title: news.title,
                content: news.content,
                author: news.author ? `${news.author.firstName} ${news.author.lastName}` : '',
                categories: news.categories ? news.categories.map(category => category.name).join(', ') : '',
                imagePath: news.imagePath,
            })),
            total: newsList.length,
        };
    },
    getOne: async (resource, params) => {
        const state = store.getState();
        const newsState = state.news;

        if (!newsState.newsList || newsState.newsList.length === 0) {
            await store.dispatch(getNewsListAction());
        }

        const news = state.news.newsList.find(n => n.id === Number(params.id));
        if (!news) {
            throw new Error(`News with id ${params.id} not found`);
        }

        return { data: news };
    },
    create: async (resource, params) => {
        let imagePath = null;

        if (params.data.image && params.data.image.rawFile) {
            const formData = new FormData();
            formData.append("file", params.data.image.rawFile);
            await store.dispatch(uploadNewsPhotoAction(formData));
            const state = store.getState();
            imagePath = state.news.photo;
        }

        const newsData = {
            title: params.data.title,
            content: params.data.content,
            categories: params.data.categories,
            imagePath: imagePath
        };

        await store.dispatch(createNewsAction(newsData));

        const state = store.getState();
        const createdNews = state.news.article;

        return { data: createdNews };
    },

    update: async (resource, params) => {
        let imagePath = params.previousData.imagePath;

        if (params.data.image && params.data.image.rawFile) {
            const formData = new FormData();
            formData.append("file", params.data.image.rawFile);
            await store.dispatch(uploadNewsPhotoAction(formData));
            const state = store.getState();
            imagePath = state.news.photo;
        }

        const newsData = {
            title: params.data.title,
            content: params.data.content,
            categories: params.data.categories,
            imagePath: imagePath,
        };

        await store.dispatch(updateNewsAction(params.id, newsData));

        const state = store.getState();
        const updatedNews = state.news.article;

        return { data: updatedNews };
    },

    delete: async (resource, params) => {
        const { id } = params;
        await store.dispatch(deleteNewsAction(id));
        return { data: { id } };
    },
};

export default newsDataProvider;

