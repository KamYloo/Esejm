import React, { useState, useEffect } from "react";
import { FaTimes } from "react-icons/fa";
import {useDispatch} from "react-redux";
import {editUserPictureAction} from "../redux/UserService/Action.js";
import toast from "react-hot-toast";

export const ProfileEditor = ({ onClose, profileImg }) => {
    const userData = (() => { try { return JSON.parse(localStorage.getItem("user")) || null; } catch { return null; } })();
    const [selectedImg, setSelectedImg] = useState(null);
    const [preview, setPreview] = useState(profileImg);

    const dispatch = useDispatch()

    const handleFileChange = (e) => {
        setSelectedImg(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault()
        const formData = new FormData();
        formData.append('image', selectedImg);

        await dispatch(editUserPictureAction(formData))
            .then((res) => {
                toast.success('Image uploaded successfully.');
                const updatedUserData = { ...userData, profileImage: res };
                localStorage.setItem("user", JSON.stringify(updatedUserData));
            })
            .catch(() => {
                toast.error('Failed to upload image. Please try again.');
            })
            .finally(() => {
                setSelectedImg(null);
                onClose();
            });

    };

    useEffect(() => {
        if (selectedImg) {
            const previewUrl = URL.createObjectURL(selectedImg);
            setPreview(previewUrl);
            return () => {
                URL.revokeObjectURL(previewUrl);
            };
        }
    }, [selectedImg]);

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-40 z-50">
            <div className="bg-blue-50 w-96 rounded-lg p-6 relative shadow-lg border-4 border-[#061D31]">
                <button
                    onClick={onClose}
                    className="absolute top-2 right-2 text-gray-700 hover:text-red-500"
                >
                    <FaTimes size={20} />
                </button>

                <form onSubmit={handleSubmit} className="text-center flex flex-col items-center">
                    <div className="mb-4 flex flex-col items-center">
                        <img
                            src={preview || "https://via.placeholder.com/100"}
                            alt="User Avatar"
                            className="rounded-full w-24 h-24 mb-2 object-cover border-2 border-[#061D31]"
                        />
                        <p
                            className="text-sm text-gray-600 truncate max-w-[300px] text-ellipsis"
                            style={{
                                whiteSpace: "nowrap",
                                overflow: "hidden",
                                textOverflow: "ellipsis",
                            }}
                        >
                            {selectedImg?.name || "Brak wybranego pliku"}
                        </p>
                    </div>

                    <input
                        type="file"
                        id="fileInput"
                        className="hidden"
                        onChange={handleFileChange}
                    />
                    <button
                        type="button"
                        onClick={() => document.getElementById("fileInput").click()}
                        className="bg-gray-200 hover:bg-gray-300 text-gray-800 font-semibold py-2 px-4 rounded-lg mb-4"
                    >
                        Wybierz zdjęcie
                    </button>

                    <button
                        type="submit"
                        className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg"
                    >
                        Wyślij
                    </button>
                </form>
            </div>
        </div>
    );
};
