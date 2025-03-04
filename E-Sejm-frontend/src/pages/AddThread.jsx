import React, { useState } from "react";
import AddThreadForm from "../features/ForumPanel/AddThreadForm.jsx";
import {useNavigate} from "react-router-dom";

const AddThread = () => {
    const [formState, setFormState] = useState({
        id: null,
        title: "",
        content: "",
        category: "Polska",
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormState((prev) => ({ ...prev, [name]: value }));
    };

    const handleContentChange = (value) => {
        setFormState((prev) => ({ ...prev, content: value }));
    };

    const handleAddThread = () => {
        console.log("Dodano wątek:", formState);
        setFormState({ id: null, title: "", content: "", category: "Polska" });
    };

    return (
        <AddThreadForm
            formState={formState}
            handleInputChange={handleInputChange}
            handleContentChange={handleContentChange}
            handleAddThread={handleAddThread}
        />
    );
};

export default AddThread;
