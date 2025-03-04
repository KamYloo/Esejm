import React from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const NewsForm = ({ formState, handleInputChange, handleImageUpload, handleAddNews, handleUpdateNews, handleContentChange, handleCategoryChange }) => {
    return (
        <div className="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow-md mb-8">
            <h2 className="text-xl font-semibold mb-6">{formState?.id ? "Edytuj wiadomość" : "Dodaj wiadomość"}</h2>
            <div className="space-y-16">
                {/* Tytuł */}
                <input
                    type="text"
                    name="title"
                    value={formState?.title}
                    onChange={handleInputChange}
                    placeholder="Tytuł"
                    className="p-2 border rounded w-full"
                />

                {/* Edytor treści */}
                <div className="relative">
                    <ReactQuill
                        theme="snow"
                        value={formState?.content}
                        onChange={handleContentChange}
                        placeholder="Treść wiadomości"
                        className="rounded-lg h-40"
                    />
                </div>

                {/* Przesyłanie pliku */}
                <div>
                    <input
                        id="image"
                        type="file"
                        onChange={handleImageUpload}
                        accept="image/*"
                        className="p-2 border rounded w-full"
                    />
                </div>

                <div>
                    <select
                        id="categories"
                        name="categories"
                        value={formState?.categories}
                        onChange={handleCategoryChange}
                        multiple
                        className="p-2 border rounded w-full"
                    >
                        <option value="POLSKA">Polska</option>
                        <option value="SWIAT">Świat</option>
                    </select>
                </div>

            </div>

            {/* Przycisk */}
            <div className="mt-6">
                {formState?.id ? (
                    <button
                        onClick={handleUpdateNews}
                        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                    >
                        Zaktualizuj
                    </button>
                ) : (
                    <button
                        onClick={handleAddNews}
                        className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                    >
                        Dodaj
                    </button>
                )}
            </div>
        </div>
    );
};

export default NewsForm;
