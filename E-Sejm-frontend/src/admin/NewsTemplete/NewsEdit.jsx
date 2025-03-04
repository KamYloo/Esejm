import {Edit, SimpleForm, TextInput, FileInput, FileField, SelectArrayInput} from 'react-admin';

const NewsEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="title" label="Title" />
            <TextInput source="content" label="Content" multiline />
            <SelectArrayInput
                source="categories"
                label="Categories"
                choices={[
                    { id: 'SWIAT', name: 'Świat' },
                    { id: 'POLSKA', name: 'Polska' },
                ]}
            />
            <FileInput source="image" label="Image" accept="image/*">
                <FileField source="src" title="title" />
            </FileInput>
        </SimpleForm>
    </Edit>
);

export default NewsEdit;
