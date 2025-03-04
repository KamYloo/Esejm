import {store} from "../../redux/store.js";
import {deleteUserAction, getUsersAction, updateUserAction} from "../../redux/UserService/Action.js";

const userDataProvider = {
    getList: async () => {
        await store.dispatch(getUsersAction());
        const state = store.getState();
        const userState = state.user;
        const users = userState.users || [];
        return {
            data: users.map(user => ({
                id: user.id,
                email: user.email,
                firstName: user.firstName,
                lastName: user.lastName,
                nickName: user.nickName,
                roles: user.roles,
            })),
            total: users.length,
        };
    },

    getOne: async (resource, params) => {
        const state = store.getState();
        const userState = state.user;
        const user = userState.users.find(u => u.id === Number(params.id));

        if (!user) {
            throw new Error(`User with id ${params.id} not found`);
        }

        return { data: user };
    },

    update: async (resource, params) => {
        const { id, data } = params;

        const cleanedData = {
            ...data,
            roles: Array.from(new Set(data.roles || [])),
        };

        await store.dispatch(updateUserAction(id, cleanedData));
        return { data: cleanedData };
    },

    delete: async (resource, params) => {
        const { id } = params;
        await store.dispatch(deleteUserAction(id));
        return { data: { id } };
    },

};

export default userDataProvider;