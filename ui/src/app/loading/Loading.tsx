import classnames from 'classnames';
import React from 'react';
import { Container, Spinner } from 'react-bootstrap';
import './_styles.scss';

export default class Loading extends React.Component<Props, State> {
    render() {
        const { message } = this.props;

        return (
            <Container fluid className='loading-container vh-75 d-flex flex-column align-items-center'>
                {message && <header className='message'>{message}</header>}
                <Spinner className={classnames('spinner', { message })} animation='border' />
            </Container>
        );
    }
}

type Props = {
    message?: string;
};

type State = {};