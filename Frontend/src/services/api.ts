import axios from "axios";

const getUsersApi = () => {
    const url = `${import.meta.env.VITE_BACKEND_URL}/users`;
    return axios.get(url);
}

const createUserApi = (name: string, email: string) => {
    const url = `${import.meta.env.VITE_BACKEND_URL}/users`;
    return axios.post(url, { name, email })
}

const updateUserApi = (id: number, name: string, email: string) => {
    const url = `${import.meta.env.VITE_BACKEND_URL}/users/${id}`;
    return axios.put(url, { id, name, email })
}

const deleteUserApi = (id: number) => {
    const url = `${import.meta.env.VITE_BACKEND_URL}/users/${id}`;
    return axios.delete(url)
}

export {
    getUsersApi, createUserApi, updateUserApi, deleteUserApi
}