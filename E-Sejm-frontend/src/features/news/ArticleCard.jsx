import React from 'react';

const ArticleCard = ({ title, description, author, image, onClick  }) => {
    return (
        <div
            className="bg-white shadow-md rounded-md overflow-visible cursor-pointer transform transition duration-300 hover:scale-105 hover:shadow-lg"
            style={{ willChange: 'transform' }}  onClick={onClick}>
            <img
                src={image}
                alt={title}
                className="w-full h-48 object-cover rounded-t-md"
            />
            <div className="p-4">
                <h2 className="text-lg font-semibold mb-2">{title}</h2>
                <div className="text-gray-500 text-sm mb-3">
                    <span>{new Date().toLocaleDateString()}</span>
                    <span className="mx-2">•</span>
                    <span>{author.firstName} {author.lastName}</span>
                </div>
                <div
                    className="text-gray-700 text-sm"
                    dangerouslySetInnerHTML={{__html: description}}
                ></div>
            </div>
        </div>
    );
};

export {ArticleCard};
