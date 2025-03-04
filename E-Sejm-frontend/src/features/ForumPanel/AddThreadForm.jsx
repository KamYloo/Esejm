import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { addPostAction } from "../../redux/PostService/Action.js";
import { useNavigate } from "react-router-dom";
import { MdKeyboardCommandKey } from "react-icons/md";

function AddThreadForm() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const categoriesList = [
        { id: 1, name: "POLSKA" },
        { id: 2, name: "SWIAT" },
        { id: 3, name: "WYBORY" },
        { id: 4, name: "WOJSKO" },
        { id: 5, name: "GOSPODARKA" },
        { id: 6, name: "EKOLOGIA" },
        { id: 7, name: "FINANSE" },
    ];

    const [formState, setFormState] = useState({
        title: "",
        content: "",
        categories: ["Polska"],
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormState((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleContentChange = (content) => {
        setFormState((prev) => ({
            ...prev,
            content,
        }));
    };

    const handleCategoryChange = (e) => {
        const selectedOptions = Array.from(e.target.selectedOptions).map(
            (option) => option.value
        );
        setFormState((prev) => ({
            ...prev,
            categories: selectedOptions,
        }));
    };

    const handleAddThread = () => {
        console.log("Wybrane kategorie:", formState.categories);

        const postRequestDto = {
            title: formState.title.trim(),
            content: formState.content.trim(),
            categories: formState.categories,
        };

        console.log("Dane do wysłania:", postRequestDto);

             dispatch(addPostAction(postRequestDto));
            alert("Wątek został pomyślnie dodany!");

            setFormState({
                title: "",
                content: "",
                categories: ["Polska"],
            });

            navigate('/forum');
    };

    return (
        <div className="min-h-screen bg-gray-100 p-16 flex justify-center items-center">
            <div className="w-full max-w-3xl bg-white p-6 rounded-lg shadow-md">
                <h1 className="text-3xl font-bold text-center mb-6">Dodaj wątek</h1>
                <form>
                    <div className="mb-4">
                        <label className="block text-sm font-bold mb-2" htmlFor="title">
                            Temat wątku
                        </label>
                        <input
                            type="text"
                            id="title"
                            name="title"
                            value={formState.title}
                            onChange={handleInputChange}
                            className="w-full p-2 border rounded"
                            placeholder="Wpisz temat"
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-sm font-bold mb-2" htmlFor="content">
                            Treść wątku
                        </label>
                        <textarea
                            id="content"
                            name="content"
                            value={formState.content}
                            onChange={(e) => handleContentChange(e.target.value)}
                            className="w-full p-2 border rounded"
                            rows="5"
                            placeholder="Wpisz treść wątku"
                        ></textarea>
                    </div>
                    <div className="mb-4">
                        <label className="block text-sm font-bold mb-2" htmlFor="categories">
                            Kategorie
                        </label>
                        <select
                            id="categories"
                            name="categories"
                            multiple
                            value={formState.categories}
                            onChange={handleCategoryChange}
                            className="w-full p-2 border rounded"
                        >
                            {categoriesList.map((category) => (
                                <option key={category.id} value={category.name}>
                                    {category.name}
                                </option>
                            ))}
                        </select>
                        <small className="text-gray-500 inline">
                            Przytrzymaj Ctrl (lub Cmd <MdKeyboardCommandKey style={{display: "inline"}}/> na Macu), aby wybrać wiele kategorii.
                        </small>


                    </div>
                    <button
                        type="button"
                        onClick={handleAddThread}
                        className="bg-blue-900 text-white px-4 py-2 rounded hover:bg-blue-800"
                    >
                        Dodaj wątek
                    </button>
                </form>
            </div>
        </div>
    );
}

export default AddThreadForm;
