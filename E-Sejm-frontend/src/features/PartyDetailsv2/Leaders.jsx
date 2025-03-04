import React from "react";

const Leaders = () => (
    <div className="bg-white shadow-xl rounded-lg p-6 mb-8">
        <h2 className="text-2xl font-bold text-gray-800 mb-4 text-center">Liderzy</h2>
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-8">
            <Leader name="Sławomir Metzen" image="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQG1JnQ2uHdrkeBM2_YdJNuIxi0Qnr8pfb0OSsEqd-cROeGLu0K" />
            <Leader name="Grzegorz Braun" image="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRk0Te0J0penxHEwYF1d1VRHrSYLiNmdUIbDQ&s" />
            <Leader name="Krzysztof Bosak" image="https://scontent-waw2-2.xx.fbcdn.net/v/t39.30808-6/402360510_889434125872169_2043848396048248079_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=f3wdyWjq1I8Q7kNvgFQWaDx&_nc_zt=23&_nc_ht=scontent-waw2-2.xx&_nc_gid=AOi43WCV5jGrTAtrurZUWd3&oh=00_AYBBiEye2dQHQxrHpHs47o1gc5JHfIgNbmy9StKgihtAfQ&oe=676A5E4A" />
        </div>
    </div>
);

const Leader = ({ name, image }) => (
    <div className="flex flex-col items-center">
        <div className="w-48 h-[75%] bg-gray-200 rounded-lg mb-4 overflow-hidden">
            <img
                src={image}
                alt={name}
                className="object-cover w-full h-full"
            />
        </div>
        <p className="text-lg font-bold text-gray-800">{name}</p>
    </div>
);

export default Leaders;
