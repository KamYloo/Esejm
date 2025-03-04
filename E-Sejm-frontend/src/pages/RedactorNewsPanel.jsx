import React, { useEffect, useState } from "react";
import NewsForm from "../features/RedactorNewsPanel/NewsForm.jsx";
import NewsList from "../features/RedactorNewsPanel/NewsList.jsx";
import { useDispatch, useSelector } from "react-redux";
import {
    createNewsAction, deleteNewsAction,
    getNewsListAction,
    updateNewsAction,
    uploadNewsPhotoAction
} from "../redux/NewsService/Action.js";
import toast from "react-hot-toast";

const RedactorNewsPanel = () => {
    const [imageFile, setImageFile] = useState(null);
    const userData = (() => { try { return JSON.parse(localStorage.getItem("user")) || null; } catch { return null; } })();
    const dispatch = useDispatch();
    const { newsList, article, photo } = useSelector(store => store.news);

    const [formState, setFormState] = useState({
        id: null,
        title: "",
        content: "",
        imagePath: "",
        categories: ["Polska"],
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormState((prev) => ({ ...prev, [name]: value }));
    };

    const handleContentChange = (value) => {
        setFormState((prev) => ({ ...prev, content: value }));
    };

    const handleImageUpload = (e) => {
        const file = e.target.files[0];
        if (file) {
            setImageFile(file);
        }
    };

    const handleCategoryChange = (e) => {
        const selectedCategories = Array.from(e.target.selectedOptions, option => option.value);
        setFormState((prev) => ({ ...prev, categories: selectedCategories }));
    };

    const handleAddNews = async () => {
        if (!imageFile) {
            toast.error("Please upload an image before submitting the article.");
            console.error("Brak zdjęcia do przesłania.");
            return;
        }

        const formData = new FormData();
        formData.append("file", imageFile);

        try {
            let uploadedPhotoPath;
            try {
                uploadedPhotoPath = await dispatch(uploadNewsPhotoAction(formData));
            } catch (uploadError) {
                console.error("Błąd przesyłania zdjęcia:", uploadError);
                toast.error("Failed to upload the image. Please try again.");
                return;
            }

            const newNews = {
                ...formState,
                imagePath: uploadedPhotoPath,
            };

            try {
                await dispatch(createNewsAction(newNews));
                toast.success("Article created successfully!");

                setFormState({ id: null, title: "", content: "", imagePath: "", categories: ["Polska"] });
                setImageFile(null);
            } catch (createError) {
                console.error("Błąd podczas tworzenia artykułu:", createError);
                toast.error("Failed to create the article. Please try again.");
            }
        } catch (error) {
            console.error("Nieoczekiwany błąd:", error);
            toast.error("An unexpected error occurred. Please try again later.");
        }
    };

    const handleEditNews = (id) => {
        const newsToEdit = newsList.find((item) => item.id === id);
        setFormState(newsToEdit);
    };

    const handleUpdateNews = async () => {
        let updatedImagePath = formState.imagePath;

        if (imageFile) {
            const formData = new FormData();
            formData.append("file", imageFile);

            try {
                updatedImagePath = await dispatch(uploadNewsPhotoAction(formData));
            } catch (uploadError) {
                console.error("Błąd przesyłania zdjęcia:", uploadError);
                toast.error("Failed to upload the new image. Please try again.");
                return;
            }
        }

        const updatedNews = {
            ...formState,
            imagePath: updatedImagePath,
        };

        try {
            await dispatch(updateNewsAction(formState.id, updatedNews));
            toast.success("Article updated successfully!");

            setFormState({ id: null, title: "", content: "", imagePath: "", categories: ["Polska"] });
            setImageFile(null);
        } catch (updateError) {
            console.error("Błąd podczas aktualizacji artykułu:", updateError);
            toast.error("Failed to update the article. Please try again.");
        }
    };

    const handleDeleteNews = (id) => {
        dispatch(deleteNewsAction(id))
            .then(() => {
                toast.success('Article deleted successfully');
            })
            .catch(() => {
                toast.error("Failed to delete article. Please try again.");
            })
    };

    useEffect(() => {
        dispatch(getNewsListAction())
    }, [dispatch, article, photo]);

    return (
        <div className="min-h-screen bg-gray-100 p-16">
            <h1 className="text-3xl font-bold text-center mb-8">Panel Redaktora - Wiadomości</h1>
            <NewsForm
                formState={formState}
                handleInputChange={handleInputChange}
                handleImageUpload={handleImageUpload}
                handleContentChange={handleContentChange}
                handleAddNews={handleAddNews}
                handleUpdateNews={handleUpdateNews}
                handleCategoryChange={handleCategoryChange}
            />
            <NewsList
                news={newsList}
                handleEditNews={handleEditNews}
                handleDeleteNews={handleDeleteNews}
            />
        </div>
    );
};

export default RedactorNewsPanel;
