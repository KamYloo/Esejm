import React from "react";
import NewsItem from "./NewsItem.jsx";

const NewsList = ({ news, handleEditNews, handleDeleteNews }) => {
    return (
        <div className="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-4">Lista wiadomości</h2>
            <ul>
                {news.map((item) => (
                    <NewsItem
                        key={item.id}
                        item={item}
                        handleEditNews={handleEditNews}
                        handleDeleteNews={handleDeleteNews}
                    />
                ))}
            </ul>
        </div>
    );
};

export default NewsList;
