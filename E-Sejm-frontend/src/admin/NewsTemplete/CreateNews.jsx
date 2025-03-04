import {Create, SimpleForm, TextInput, FileInput, FileField, SelectArrayInput} from 'react-admin';

const NewsCreate = () => (
    <Create>
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
    </Create>
);

export default NewsCreate;

